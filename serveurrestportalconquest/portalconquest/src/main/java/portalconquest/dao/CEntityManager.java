package portalconquest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Screetts on 26/05/2016.
 */
public class CEntityManager {

    private  EntityManagerFactory emf = Persistence.createEntityManagerFactory("testpostgresqllocal");
    private  EntityManager em = emf.createEntityManager();

    private static CEntityManager INSTANCE = null;

    public static CEntityManager getINSTANCE(){
        if(INSTANCE == null)
            INSTANCE = new CEntityManager();
        return INSTANCE;
    }

    private CEntityManager() {}

    public EntityManager getEm() {
        return em;
    }
}
