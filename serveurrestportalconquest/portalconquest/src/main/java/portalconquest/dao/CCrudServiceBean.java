package portalconquest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Screetts on 27/04/2016.
 */

public class CCrudServiceBean<T> implements ICrudService<T> {

    public static CEntityManager cEntityManager = CEntityManager.getINSTANCE();

    public static EntityManager em = cEntityManager.getEm();

    public  T create(T t) {
        this.em.merge(t);
        this.em.flush();
        return t;
    }

    @SuppressWarnings("unchecked")
    public  T find(Class type,Object id) {
        return (T) this.em.find(type, id);
    }

    public void delete(Class type,Object id) {
        Object ref = this.em.getReference(type, id);
        this.em.remove(ref);
    }


    public  T update(T t) {
        t = (T)this.em.merge(t);
        return t;
    }

    public List findWithNamedQuery(String namedQueryName){
        return this.em.createNamedQuery(namedQueryName).getResultList();
    }

    public List findWithNamedQuery(String namedQueryName, Map parameters){
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    public List findWithNamedQuery(String queryName, int resultLimit) {
        return this.em.createNamedQuery(queryName).
                setMaxResults(resultLimit).
                getResultList();
    }

    public List findByNativeQuery(String sql, Class type) {
        return this.em.createNativeQuery(sql, type).getResultList();
    }

    public List findWithNamedQuery(String namedQueryName, Map parameters,int resultLimit){
        Set<Map.Entry> rawParameters = parameters.entrySet();
        Query query = this.em.createNamedQuery(namedQueryName);
        if(resultLimit > 0)
            query.setMaxResults(resultLimit);
        for (Map.Entry entry : rawParameters) {
                query.setParameter((String) entry.getKey(), entry.getValue());
            }
        return query.getResultList();
    }
}

