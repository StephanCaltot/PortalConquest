package portalconquesttest.servicestest;

import classes.CConsumable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import portalconquest.dao.ICrudService;
import portalconquest.services.CConsumableServices;
import portalconquesttest.mocks.CCrudServiceBeanMock;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import static org.junit.Assert.*;

/**
 * Created by ltonnet637 on 26/05/16.
 */
public class CConsumableServicesTest {


    private static EntityManagerFactory mFactory = Persistence.createEntityManagerFactory("unit-test-database");
    private EntityManager mManager;
    private EntityTransaction mTransaction;

    private CConsumableServices mConsumableServices;

    private CConsumable mConsumableLVL3, mConsumableLVL7;

    private final int CONSUMABLE_3_ID = 1;
    private final int CONSUMABLE_7_ID = 2;
    private final int CONSUMABLE_3_LVL = 3;
    private final int CONSUMABLE_7_LVL = 7;

    @Before
    public void setUp() throws Exception {
        mManager = mFactory.createEntityManager();
        mTransaction = mManager.getTransaction();
        mTransaction.begin();
//        mConsumableServices = new CConsumableServices((ICrudService) new CCrudServiceBeanMock());

        mConsumableLVL3 = new CConsumable();
        mConsumableLVL7 = new CConsumable();
        mConsumableLVL3.setId(CONSUMABLE_3_ID);
        mConsumableLVL7.setId(CONSUMABLE_7_ID);
        mConsumableLVL3.setLevel(CONSUMABLE_3_LVL);
        mConsumableLVL7.setLevel(CONSUMABLE_7_LVL);
    }


    @After
    public void tearDown() throws Exception {
        //On vide la base après les tests
        if(mManager.find(CConsumable.class, CONSUMABLE_3_ID) != null)
            mManager.remove(mManager.getReference(CConsumable.class, CONSUMABLE_3_ID));
        if(mManager.find(CConsumable.class, CONSUMABLE_7_ID) != null)
            mManager.remove(mManager.getReference(CConsumable.class, CONSUMABLE_7_ID));
        mTransaction.commit();

        mManager.close();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        mFactory.close();
    }

    @Test
    public void testGet() throws Exception {
        //Consumable à récupérer

        //On insère les consumables dans la BDD
        mManager.merge(mConsumableLVL3);
        mManager.merge(mConsumableLVL7);
        mManager.flush();

        //On les récupère avec la méthode à tester
        assertEquals(mConsumableLVL3.getLevel(), mConsumableServices.get(CONSUMABLE_3_LVL).getLevel());
        assertEquals(mConsumableLVL7.getLevel(), mConsumableServices.get(CONSUMABLE_7_LVL).getLevel());

    }
}