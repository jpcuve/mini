package com.messio.mini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by jpc on 1/20/15.
 */
@Table(name = "parties")
@Entity
@NamedQueries({
        @NamedQuery(name = Party.PARTY_BY_BINDER_IDS, query = "select distinct p.id, p from Party p where p.binder.id in (:ids)")
})
@JsonIgnoreProperties({"binder", "actor"})
public class Party {
    public static final String PARTY_BY_BINDER_IDS = "partyByBinderIds";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "binder_id")
    private Binder binder;
    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;
    @Basic
    @Column(name = "plaintiff")
    private boolean plaintiff;
    @Basic
    @Column(name = "binder_id", insertable = false, updatable = false)
    private Long binderId;
    @Basic
    @Column(name = "actor_id", insertable = false, updatable = false)
    private Long actorId;

    public Party() {
    }

    public Party(Binder binder, Actor actor, boolean plaintiff) {
        this.binder = binder;
        this.actor = actor;
        this.plaintiff = plaintiff;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Binder getBinder() {
        return binder;
    }

    public void setBinder(Binder binder) {
        this.binder = binder;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public boolean isPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(boolean opponent) {
        this.plaintiff = opponent;
    }

    public Long getBinderId() {
        return binderId;
    }

    public void setBinderId(Long binderId) {
        this.binderId = binderId;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }
}
