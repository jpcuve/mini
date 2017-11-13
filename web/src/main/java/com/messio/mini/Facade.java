package com.messio.mini;

import com.messio.mini.bean.model.BinderQueryModel;
import com.messio.mini.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    public Map<Long, Binder> findBinders(Collection<Long> ids){
        if (ids == null || ids.size() == 0) return Collections.emptyMap();
        return em.createNamedQuery(Binder.BINDER_BY_IDS, Object[].class)
                .setParameter("ids", ids)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(os -> (Long) os[0], os -> (Binder) os[1]));
    }

    public Map<Long, Docket> findDocketsByBinderIds(Collection<Long> binderIds){
        if (binderIds == null || binderIds.size() == 0) return Collections.emptyMap();
        return em.createNamedQuery(Docket.DOCKET_BY_BINDER_IDS, Object[].class)
                .setParameter("ids", binderIds)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(os -> (Long) os[0], os -> (Docket) os[1]));
    }

    public Map<Long, Decision> findDecisionsByDocketIds(Collection<Long> docketIds){
        if (docketIds == null || docketIds.size() == 0) return Collections.emptyMap();
        return em.createNamedQuery(Decision.DECISION_BY_DOCKET_IDS, Object[].class)
                .setParameter("ids", docketIds)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(os -> (Long) os[0], os -> (Decision) os[1]));
    }

    public Map<Long, Court> findCourts(Collection<Long> ids){
        if (ids == null || ids.size() == 0) return Collections.emptyMap();
        return em.createNamedQuery(Court.COURT_BY_IDS, Object[].class)
                .setParameter("ids", ids)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(os -> (Long) os[0], os -> (Court) os[1]));
    }

    public List<Court> findCourtsByParentByName(Court parent, String name){
        return em.createNamedQuery(Court.COURT_BY_PARENT_BY_NAME, Court.class).setParameter("parent", parent).setParameter("name", name).getResultList();
    }

    public List<Pol> findPolsByParentByName(Pol parent, String name){
        return em.createNamedQuery(Pol.POL_BY_PARENT_BY_NAME, Pol.class).setParameter("parent", parent).setParameter("name", name).getResultList();
    }

    public Map<Long, Party> findPartiesByBinderIds(Collection<Long> binderIds){
        if (binderIds == null || binderIds.size() == 0) return Collections.emptyMap();
        return em.createNamedQuery(Party.PARTY_BY_BINDER_IDS, Object[].class)
                .setParameter("ids", binderIds)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(os -> (Long) os[0], os -> (Party) os[1]));
    }

    public Map<Long, Actor> findActors(Collection<Long> ids){
        if (ids == null || ids.size() == 0) return Collections.emptyMap();
        return em.createNamedQuery(Actor.ACTOR_BY_IDS, Object[].class)
                .setParameter("ids", ids)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(os -> (Long) os[0], os -> (Actor) os[1]));
    }

    public Map<Long, Right> findRightsByBinderIds(Collection<Long> binderIds){
        if (binderIds == null || binderIds.size() == 0) return Collections.emptyMap();
        return em.createNamedQuery(Right.RIGHT_BY_BINDER_IDS, Object[].class)
                .setParameter("ids", binderIds)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(os -> (Long) os[0], os -> (Right) os[1]));
    }

    public Map<Long, Right> findRights(Collection<Long> ids){
        if (ids == null || ids.size() == 0) return Collections.emptyMap();
        return em.createNamedQuery(Right.RIGHT_BY_IDS, Object[].class)
                .setParameter("ids", ids)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(os -> (Long) os[0], os -> (Right) os[1]));
    }

    public List<Long> queryBinders(BinderQueryModel model){
        // build query
        final String ref = String.format("%%%s%%", model.getReference());
        return em.createNamedQuery(Binder.BINDER_IDS_BY_ANY_REFERENCE, Long.class).setParameter("reference", ref).getResultList();
    }
}
