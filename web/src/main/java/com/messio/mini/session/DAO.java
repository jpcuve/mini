package com.messio.mini.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jpc on 2/9/16.
 */
public class DAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DAO.class);
    @PersistenceContext(unitName = "mini")
    protected EntityManager em;

    public static <T> Builder<T> builder(Class<T> aClass){
        return new Builder<>(aClass);
    }

    public static <T> Builder<T> builder(){
        return new Builder<>(null);
    }

    public static class Builder<T> {
        private final Class<T> aClass;
        private boolean named;
        private String expression;
        private Map<String, Object> parameters = new HashMap<>();
        private int first = -1;
        private int limit = -1;

        private Builder(Class<T> aClass) {
            this.aClass = aClass;
        }

        public Builder<T> explicit(String query){
            this.named = false;
            this.expression = query;
            return this;
        }

        public Builder<T> named(String query){
            this.named = true;
            this.expression = query;
            return this;
        }

        public Builder<T> first(int first){
            this.first = first;
            return this;
        }

        public Builder<T> limit(int limit){
            this.limit = limit;
            return this;
        }

        public Builder<T> param(String param, Object value){
            parameters.put(param, value);
            return this;
        }

        public Builder<T> params(String param, Object value, String param2, Object value2){
            parameters.put(param, value);
            parameters.put(param2, value2);
            return this;
        }

        public Builder<T> params(String param, Object value, String param2, Object value2, String param3, Object value3){
            parameters.put(param, value);
            parameters.put(param2, value2);
            parameters.put(param3, value3);
            return this;
        }

        private void augment(Query q){
            for (final Map.Entry<String, ?> entry: parameters.entrySet()){
                q.setParameter(entry.getKey(), entry.getValue());
            }
            if (first >= 0){
                q.setFirstResult(first);
            }
            if (limit >= 0){
                q.setMaxResults(limit);
            }
        }

        private Query build(EntityManager em){
            final Query q = named ? em.createNamedQuery(expression) : em.createQuery(expression);
            augment(q);
            return q;
        }

        private TypedQuery<T> buildTyped(EntityManager em){
            final TypedQuery<T> q = named ? em.createNamedQuery(expression, aClass) : em.createQuery(expression, aClass);
            augment(q);
            return q;
        }

    }

    public <T> List<T> results(Builder<T> builder){
        return builder.buildTyped(em).getResultList();
    }

    public <T> T result(Builder<T> builder){
        try{
            return builder.buildTyped(em).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    public int update(Builder builder){
        return builder.build(em).executeUpdate();
    }


    public <T> T create(T t){
        refreshParents(t);
        em.persist(t);
        em.flush();
        em.refresh(t);
        return t;
    }

    public <T, ID extends Serializable> T findOne(Class<T> clazz, ID pk){
        return em.find(clazz, pk);
    }

    public <T> T update(T t){
        refreshParents(t);
        return em.merge(t);
    }

    public <T> void refreshParents(T t){
        final Object[] noArgs = new Object[0];
        try{
            final BeanInfo info = Introspector.getBeanInfo(t.getClass());
            for (final PropertyDescriptor propertyDescriptor: info.getPropertyDescriptors()){
                final Class<?> clazz = propertyDescriptor.getPropertyType();
                if (clazz.isAnnotationPresent(Entity.class)){
                    try{
                        final Object field = propertyDescriptor.getReadMethod().invoke(t, noArgs);
                        if (field != null){
//                            LOGGER.debug("refresh parents, merging: {}", field);
                            if (!em.contains(field)){
                                propertyDescriptor.getWriteMethod().invoke(t, em.merge(field));
                            }
                        }
                    } catch (IllegalAccessException| InvocationTargetException e2){
                        LOGGER.error("cannot access parent", e2);
                    }
                }
            }
        } catch(IntrospectionException e){
            LOGGER.error("cannot refresh parents", e);
        }
    }

    public <T, ID extends Serializable> void delete(Class<T> clazz, ID pk){
        em.remove(em.getReference(clazz, pk));
    }
}
