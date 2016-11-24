package testutils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by ltonnet637 on 26/05/16.
 */
public class CServiceHelper {

    public static void restartEntityManager(EntityManagerFactory pFactory, EntityManager pManager, EntityTransaction pTransaction) {
        pManager = pFactory.createEntityManager();
        pTransaction = pManager.getTransaction();
        pTransaction.begin();
    }
}
