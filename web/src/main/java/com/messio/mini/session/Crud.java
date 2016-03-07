package com.messio.mini.session;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jpc on 2/9/16.
 */
public class Crud {
    @PersistenceContext(unitName = "mini")
    protected EntityManager em;

    public static class Parameters {
        private Map<String, Object> map = new HashMap<>();

        private Parameters(String parameter, Object value){
            map.put(parameter, value);
        }

        public static Parameters with(String parameter, Object value){
            return new Parameters(parameter, value);
        }

        public static Map<String, Object> build(String param1, Object value1){
            return with(param1, value1).build();
        }

        public static Map<String, Object> build(String param1, Object value1, String param2, Object value2){
            return with(param1, value1).and(param2, value2).build();
        }

        public static Map<String, Object> build(String param1, Object value1, String param2, Object value2, String param3, Object value3){
            return with(param1, value1).and(param2, value2).and(param3, value3).build();
        }

        public Parameters and(String parameter, Object value){
            map.put(parameter, value);
            return this;
        }

        public Map<String, Object> build(){
            return map;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> T create(T t){
        assert t != null;
        em.persist(t);
        em.flush();
        em.refresh(t);
        return t;
    }

    public <T, ID extends Serializable> T findOne(Class<T> clazz, ID pk){
        assert pk != null;
        return em.find(clazz, pk);
    }

    public <T> T update(T t){
        assert t != null;
        return em.merge(t);
    }

    public <T, ID extends Serializable> void delete(Class<T> clazz, ID pk){
        assert pk != null;
        em.remove(em.getReference(clazz, pk));
    }

    private void applyParameters(Query query, Map<String, ?> parameters){
        for (final Map.Entry<String, ?> entry: parameters.entrySet()){
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }

    public <T> List<T> findByQuery(Class<T> clazz, String query){
        return findByQuery(clazz, query, null);
    }

    public <T> List<T> findByQuery(Class<T> clazz, String query, Map<String, ?> parameters){
        final TypedQuery<T> typedQuery = em.createQuery(query, clazz);
        if (parameters != null){
            applyParameters(typedQuery, parameters);
        }
        return typedQuery.getResultList();
    }

    public <T> List<T> findByNamedQuery(Class<T> clazz, String namedQuery){
        return findByNamedQuery(clazz, namedQuery, null);
    }

    public <T> List<T> findByNamedQuery(Class<T> clazz, String namedQuery, Map<String, ?> parameters){
        final TypedQuery<T> typedQuery = em.createNamedQuery(namedQuery, clazz);
        if (parameters != null){
            applyParameters(typedQuery, parameters);
        }
        return typedQuery.getResultList();
    }

    public int updateByNamedQuery(String namedQuery){
        return updateByNamedQuery(namedQuery, null);
    }

    public int updateByNamedQuery(String namedQuery, Map<String, ?> parameters){
        final Query query = em.createNamedQuery(namedQuery);
        if (parameters != null){
            applyParameters(query, parameters);
        }
        return query.executeUpdate();
    }
}
