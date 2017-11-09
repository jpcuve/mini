package com.messio.mini;

import com.messio.mini.bean.model.BinderQueryModel;
import com.messio.mini.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by jpc on 13-07-16.
 */
@Repository
@Transactional
public class Facade {
    @PersistenceContext
    private EntityManager em;

    public <T> T create(T t){
        Objects.requireNonNull(t);
        em.persist(t);
        em.flush();
        em.refresh(t);
        return t;
    }

    public <T, ID extends Serializable> T findOne(Class<T> clazz, ID pk){
        Objects.requireNonNull(pk);
        return em.find(clazz, pk);
    }

    public <T> T update(T t){
        Objects.requireNonNull(t);
        return em.merge(t);
    }

    public <T, ID extends Serializable> void delete(Class<T> clazz, ID pk){
        Objects.requireNonNull(pk);
        em.remove(em.getReference(clazz, pk));
    }

/*
    final List<Binder> binders = facade.results(DAO.builder(Binder.class).named(Binder.BINDER_BY_IDS).param("ids", binderIds));
    final List<Docket> dockets = facade.results(DAO.builder(Docket.class).named(Docket.DOCKET_BY_BINDER_IDS).param("ids", binderIds));
    final List<Long> docketIds = dockets.stream().map(Docket::getId).collect(Collectors.toList());
    final List<Decision> decisions = facade.results(DAO.builder(Decision.class).named(Decision.DECISION_BY_DOCKET_IDS).param("ids", docketIds));
    final List<Long> courtIds = dockets.stream().map(Docket::getCourtId).collect(Collectors.toList());
    final List<Court> courts = facade.results(DAO.builder(Court.class).named(Court.COURT_BY_IDS).param("ids", courtIds));
*/

    public long countBinderIds(){
        return em.createNamedQuery(Binder.BINDER_COUNT_IDS, Long.class).getSingleResult();
    }

    public List<Binder> findBinders(Collection<Long> ids){
        return em.createNamedQuery(Binder.BINDER_BY_IDS, Binder.class).setParameter("ids", ids).getResultList();
    }

    public List<Docket> findDocketsByBinderIds(Collection<Long> binderIds){
        return em.createNamedQuery(Docket.DOCKET_BY_BINDER_IDS, Docket.class).setParameter("ids", binderIds).getResultList();
    }

    public List<Decision> findDecisionsByDocketIds(Collection<Long> docketIds){
        return em.createNamedQuery(Decision.DECISION_BY_DOCKET_IDS, Decision.class).setParameter("ids", docketIds).getResultList();
    }

    public List<Court> findCourts(Collection<Long> ids){
        return em.createNamedQuery(Court.COURT_BY_IDS, Court.class).setParameter("ids", ids).getResultList();
    }

    public List<Court> findCourtsByParentByName(Court parent, String name){
        return em.createNamedQuery(Court.COURT_BY_PARENT_BY_NAME, Court.class).setParameter("parent", parent).setParameter("name", name).getResultList();
    }

    public List<Pol> findPolsByParentByName(Pol parent, String name){
        return em.createNamedQuery(Pol.POL_BY_PARENT_BY_NAME, Pol.class).setParameter("parent", parent).setParameter("name", name).getResultList();
    }

    public List<Long> queryBinders(BinderQueryModel model){
        // build query
        final String ref = String.format("%%%s%%", model.getReference());
        return em.createNamedQuery(Binder.BINDER_IDS_BY_ANY_REFERENCE, Long.class).setParameter("reference", ref).getResultList();
    }
}
