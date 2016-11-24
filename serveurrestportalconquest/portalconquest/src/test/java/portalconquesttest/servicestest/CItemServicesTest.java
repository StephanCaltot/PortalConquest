package portalconquesttest.servicestest;

import classes.AItem;
import classes.CRangeBoost;
import classes.CResonator;
import classes.CWeapon;
import org.hamcrest.Matchers;
import org.junit.*;
import portalconquest.dao.ICrudService;
import portalconquest.services.CItemServices;
import portalconquesttest.mocks.CCrudServiceBeanMock;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import static org.junit.Assert.*;

/**
 * Created by ltonnet637 on 26/05/16.
 */

public class CItemServicesTest {

    private final int WEAPON_ID = 1;
    private final int RESONATOR_ID = 2;
    private final int RANGEBOOST_ID = 3;
    private final int WEAPON_OLD_ATTACK_VALUE = 15;
    private final int WEAPON_NEW_ATTACK_VALUE = 19;
    private final int RESONATOR_OLD_LEVEL = 3;
    private final int RESONATOR_NEW_LEVEL = 8;

    private static EntityManagerFactory mFactory = Persistence.createEntityManagerFactory("unit-test-database");
    private EntityManager mManager;
    private EntityTransaction mTransaction;

    private CItemServices mItemService;

    private AItem mWeapon, mResonator, mRangeBoost;


    @Before
    public void setUp() throws Exception {
        mManager = mFactory.createEntityManager();
        mTransaction = mManager.getTransaction();
        mTransaction.begin();

//        mItemService = new CItemServices((ICrudService) new CCrudServiceBeanMock());

        mWeapon = new CWeapon();
        mResonator = new CResonator();
        mRangeBoost = new CRangeBoost();
        mWeapon.setId(WEAPON_ID);
        mResonator.setId(RESONATOR_ID);
        mRangeBoost.setId(RANGEBOOST_ID);
    }


    @After
    public void tearDown() throws Exception {
        if(mManager.find(AItem.class, WEAPON_ID) != null)
            mManager.remove(mManager.getReference(AItem.class, WEAPON_ID));
        if(mManager.find(AItem.class, RESONATOR_ID) != null)
            mManager.remove(mManager.getReference(AItem.class, RESONATOR_ID));
        if(mManager.find(AItem.class, RANGEBOOST_ID) != null)
            mManager.remove(mManager.getReference(AItem.class, RANGEBOOST_ID));
        mTransaction.commit();

        mManager.close();
    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        mFactory.close();
    }


    @Test
    public void testGetItems() throws Exception {

        //Insertion "manuelle" des items dans la base
        mManager.merge(mWeapon);
        mManager.merge(mResonator);
        mManager.flush();

        //On vérifie que la méthode à tester récupère les bons items
        assertEquals(mWeapon, mItemService.getItems(WEAPON_ID));
        assertEquals(mResonator,mItemService.getItems(RESONATOR_ID));
    }


    @Test
    public void testGetItemsAll() throws Exception {

        //Insertion "manuelle" des items dans la base
        mManager.merge(mWeapon);
        mManager.merge(mResonator);
        mManager.merge(mRangeBoost);
        mManager.flush();

        //On teste si la méthode renvoie bien tous les items
        assertThat(mItemService.getItemsAll(), Matchers.containsInAnyOrder(mWeapon, mResonator, mRangeBoost));
    }


    @Test
    public void testPutItem() throws Exception {
        //On insère les items dans la base
        ((CWeapon)mWeapon).setAttackValue(WEAPON_OLD_ATTACK_VALUE);
        ((CResonator)mResonator).setLevel(RESONATOR_OLD_LEVEL);
        mManager.merge(mWeapon);
        mManager.merge(mResonator);
        mTransaction.commit();
        mManager.close();

        //On les modifie avec la fonction à tester
        ((CWeapon)mWeapon).setAttackValue(WEAPON_NEW_ATTACK_VALUE);
        ((CResonator)mResonator).setLevel(RESONATOR_NEW_LEVEL);
//        mItemService.putItem(mWeapon);
//        mItemService.putItem(mResonator);

        //On vérifie qu'ils aient bien été mis à jour
        mManager = mFactory.createEntityManager();
        mTransaction = mManager.getTransaction();
        mTransaction.begin();

        assertEquals(WEAPON_NEW_ATTACK_VALUE, mManager.find(CWeapon.class, WEAPON_ID).getAttackValue());
        assertEquals(RESONATOR_NEW_LEVEL, mManager.find(CResonator.class, RESONATOR_ID).getLevel());
    }


    @Test
    public void testPostItem() throws Exception {
        //On insère les items dans la base en utilisant la méthode à tester
        mItemService.postItem(mWeapon);
        mItemService.postItem(mRangeBoost);

        //On vérifie qu'ils soient bien dans la base
        assertEquals(mWeapon, mManager.find(CWeapon.class, WEAPON_ID));
        assertEquals(mRangeBoost, mManager.find(CRangeBoost.class, RANGEBOOST_ID));
    }


    @Test
    public void testDeleteItem() throws Exception {
        //On insère manuellement les items dans la base
        mManager.merge(mWeapon);
        mManager.merge(mResonator);
        mTransaction.commit();
        mManager.close();

        //On supprime un élément grâce à la méthode à tester
        mItemService.deleteItem(RESONATOR_ID);

        //On vérifie que l'item ne soit plus là
        mManager = mFactory.createEntityManager();
        mTransaction = mManager.getTransaction();
        mTransaction.begin();
        assertNull(mManager.find(AItem.class, RESONATOR_ID));
        assertNotNull(mManager.find(AItem.class, WEAPON_ID));
    }
}