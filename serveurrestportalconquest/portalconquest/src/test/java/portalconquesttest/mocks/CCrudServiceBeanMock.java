package portalconquesttest.mocks;

import portalconquest.dao.ICrudService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ltonnet637 on 25/05/16.
 */
public class CCrudServiceBeanMock<T> implements ICrudService<T> {


    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit-test-database");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public T create(T t) {

        entityManager.getTransaction().begin();
        entityManager.merge(t);
        entityManager.getTransaction().commit();

        return t;
    }

    public T find(Class type, Object id) {
        T t = (T) this.entityManager.find(type, id);
        return t;
    }

    public T update(T t) {
        entityManager.getTransaction().begin();
        t = entityManager.merge(t);
        entityManager.getTransaction().commit();

        return t;
    }

    public void delete(Class type, Object id) {
        entityManager.getTransaction().begin();
        Object ref = entityManager.getReference(type, id);
        entityManager.remove(ref);
        entityManager.getTransaction().commit();
    }

    public List findWithNamedQuery(String queryName) {
        return entityManager.createNamedQuery(queryName).getResultList();
    }

    public List findWithNamedQuery(String queryName, int resultLimit) {
        return entityManager.createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
    }

    public List findWithNamedQuery(String namedQueryName, Map parameters) {
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    public List findWithNamedQuery(String namedQueryName, Map parameters, int resultLimit) {
        Set<Map.Entry> rawParameters = parameters.entrySet();
        Query query = entityManager.createNamedQuery(namedQueryName);
        if(resultLimit > 0)
            query.setMaxResults(resultLimit);
        for (Map.Entry entry : rawParameters)
            query.setParameter((String) entry.getKey(), entry.getValue());

        return query.getResultList();
    }
}
