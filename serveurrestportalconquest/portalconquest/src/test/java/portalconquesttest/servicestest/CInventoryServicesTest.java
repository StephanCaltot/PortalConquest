//package portalconquesttest.servicestest;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.Test;
//import portalconquest.classes.*;
//import portalconquest.services.CInventoryServices;
//import portalconquesttest.mocks.CCrudServiceBeanMock;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
//
//import static org.junit.Assert.*;
//
///**
// * Created by ltonnet637 on 26/05/16.
// */
//public class CInventoryServicesTest {
//
//
//    private static EntityManagerFactory mFactory = Persistence.createEntityManagerFactory("unit-test-database");
//    private EntityManager mManager;
//    private EntityTransaction mTransaction;
//
//    private CInventoryServices mInventoryServices;
//
//
//    @Before
//    public void setUp() throws Exception {
//        mManager = mFactory.createEntityManager();
//        mTransaction = mManager.getTransaction();
//        mTransaction.begin();
//        mInventoryServices = new CInventoryServices(new CCrudServiceBeanMock());
//    }
//
//
//    @After
//    public void tearDown() throws Exception {
//        mManager.close();
//    }
//
//
//    @AfterClass
//    public static void tearDownAfterClass() throws Exception {
//        mFactory.close();
//    }
//
//
//    @Test
//    public void testGetInventory() throws Exception {
//        //On met en place l'inventaire à insérer dans la base
//        CInventory lInventory = new CInventory();
//        CPlayer lPlayer = new CPlayer();
//        lPlayer.setId(99);
//        AItem lWeapon = new CWeapon();
//        lWeapon.setId(89);
//
//        CInventoryId lInventoryId = new CInventoryId();
//        lInventoryId.setPlayer(99);
//        lInventoryId.setItem(89);
//
//        lInventory.setPlayer(lPlayer);
//        lInventory.setItem(lWeapon);
//
//        //On insère "manuellement" l'inventaire dans la base
//        mManager.merge(lInventory);
//        mManager.flush();
//
//        //On récupère l'inventaire avec la méthode à tester
//        CInventory lNewInventory = mInventoryServices.getInventory(99, 89);
//
//        assertEquals(lPlayer, lNewInventory.getPlayer());
//        assertEquals(lWeapon, lNewInventory.getItem());
//
//        //On vide la base
//        mManager.remove(mManager.getReference(CInventory.class, lInventoryId));
//        mManager.remove(mManager.getReference(CPlayer.class, 99));
//        mManager.remove(mManager.getReference(CWeapon.class, 89));
//        mTransaction.commit();
//    }
//
//
//    @Test
//    public void testPutInventory() throws Exception {
//        //Données à insérer dans la base
//        CPlayer lPlayer = new CPlayer();
//        lPlayer.setId(12);
//        AItem lWeapon = new CWeapon();
//        lWeapon.setId(13);
//
//        CInventoryId lInventoryId = new CInventoryId();
//        lInventoryId.setPlayer(12);
//        lInventoryId.setItem(13);
//
//        CInventory lInventory = new CInventory();
//        lInventory.setQuantity(23);
//        lInventory.setPlayer(lPlayer);
//        lInventory.setItem(lWeapon);
//
//        //On insère manuellement l'inventaire dans la BDD
//        mManager.merge(lInventory);
//        mTransaction.commit();
//        mManager.close();
//
//        //On le modifie avec la méthode à tester
//        lInventory.setQuantity(14);
//        mInventoryServices.putInventory(lInventory);
//
//        //On vérifie que la modification ait bien eu lieu
//        mManager = mFactory.createEntityManager();
//        mTransaction = mManager.getTransaction();
//        mTransaction.begin();
//        assertEquals(lInventory.getQuantity(), mManager.find(CInventory.class, lInventoryId).getQuantity());
//    }
//
//    @Test
//    public void testPostInventory() throws Exception {
//        //On prépare les données à insérer dans la base
//        CPlayer lPlayer = new CPlayer();
//        lPlayer.setId(55);
//        AItem lResonator = new CResonator();
//        lResonator.setId(57);
//
//        CInventoryId lInventoryId = new CInventoryId();
//        lInventoryId.setPlayer(55);
//        lInventoryId.setItem(57);
//
//        CInventory lInventory = new CInventory();
//        lInventory.setPlayer(lPlayer);
//        lInventory.setItem(lResonator);
//        lInventory.setQuantity(153);
//
//        //On insère l'inventory dans la base avec la méthode à tester
//        mInventoryServices.postInventory(lInventory);
//
//        //On vérifie que l'inventory ait bien été inséré dans la base
//        assertEquals(lInventory.getQuantity(), mManager.find(CInventory.class, lInventoryId).getQuantity());
//
//        //On vide la base
//        mManager.remove(mManager.getReference(CInventory.class, lInventoryId));
//        mManager.remove(mManager.getReference(CPlayer.class, 55));
//        mManager.remove(mManager.getReference(CResonator.class, 57));
//    }
//}