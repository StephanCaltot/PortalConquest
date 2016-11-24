package portalconquesttest.servicestest;

import classes.CField;
import org.hamcrest.Matchers;
import org.junit.*;
import portalconquest.dao.ICrudService;
import portalconquest.services.CFieldServices;
import portalconquesttest.mocks.CCrudServiceBeanMock;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

/**
 * Created by ltonnet637 on 26/05/16.
 */

public class CFieldServicesTest {


    private final double OLD_FIELD_SURFACE = 26.4;
    private final double NEW_FIELD_SURFACE = 10.5;

    private static EntityManagerFactory mFactory = Persistence.createEntityManagerFactory("unit-test-database");
    private EntityManager mManager;
    private EntityTransaction mTransaction;

    private CFieldServices mFieldService;
    private CField mField1, mField2;

    private final int FIELD_1_ID = 1;
    private final int FIELD_2_ID = 2;


    @Before
    public void setUp() throws Exception {
        mManager = mFactory.createEntityManager();
        mTransaction = mManager.getTransaction();
        mTransaction.begin();

//        mFieldService = new CFieldServices((ICrudService) new CCrudServiceBeanMock());

        mField1 = new CField();
        mField2 = new CField();
        mField1.setId(FIELD_1_ID);
        mField2.setId(FIELD_2_ID);
    }


    @After
    public void tearDown() throws Exception {
        //On vide la BDD après les tests
        //TODO: IMPLANTER UNE SOLUTION PLUS GENERIQUE
        if(mManager.find(CField.class, FIELD_1_ID) != null)
            mManager.remove(mManager.getReference(CField.class, FIELD_1_ID));
        if(mManager.find(CField.class, FIELD_2_ID) != null)
            mManager.remove(mManager.getReference(CField.class, FIELD_2_ID));
        mTransaction.commit();

        mManager.close();
    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        mFactory.close();
    }


    @Test
    public void testGetField() throws Exception {
        //On insère manuellement les fields dans la base
        mManager.merge(mField1);
        mManager.merge(mField2);
        mManager.flush();

        //On récupère les fields avec la méthode à tester
        assertEquals(mField1, mFieldService.getField(FIELD_1_ID));
        assertEquals(mField2, mFieldService.getField(FIELD_2_ID));

    }


    @Test
    public void testGetFieldAll() throws Exception {
        //On insère manuellement les fields dans la base
        mManager.merge(mField1);
        mManager.merge(mField2);
        mManager.flush();

        //On récupère les fields avec la méthode à tester
        assertThat(mFieldService.getFieldAll(), Matchers.containsInAnyOrder(mField1, mField2));

    }


    @Test
    public void testPutField() throws Exception {
        //On insère manuellement le field dans la base
        mField1.setSurface(OLD_FIELD_SURFACE);
        mManager.merge(mField1);
        mTransaction.commit();
        mManager.close();

        //On modifie le field avec la méthode à tester
        mField1.setSurface(NEW_FIELD_SURFACE);
        mFieldService.putField(mField1);

        //On vérifie que la valeur ait bien été changée
        mManager = mFactory.createEntityManager();
        mTransaction = mManager.getTransaction();
        mTransaction.begin();
        assertEquals(NEW_FIELD_SURFACE, mManager.find(CField.class, FIELD_1_ID).getSurface(), 0);
    }


    @Test
    public void testPostField() throws Exception {
        //On insère les fields dans la base à l'aide de la méthode à tester
        mFieldService.postField(mField1);
        mFieldService.postField(mField2);

        //On vérifie que les fields aient bien été insérés
        assertEquals(mField1, mManager.find(CField.class, FIELD_1_ID));
        assertEquals(mField2, mManager.find(CField.class, FIELD_2_ID));
    }


    @Test
    public void testDeleteField() throws Exception {
        //On insère manuellement le field dans la base
        mManager.merge(mField1);
        mTransaction.commit();
        mManager.close();

        //On le supprime avec la méthode à tester
        mFieldService.deleteField(FIELD_1_ID);

        //On vérifie qu'il ait bien été supprimé
        mManager = mFactory.createEntityManager();
        mTransaction = mManager.getTransaction();
        mTransaction.begin();
        assertNull(mManager.find(CField.class, FIELD_1_ID));
    }
}