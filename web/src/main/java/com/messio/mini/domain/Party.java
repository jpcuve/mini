package com.messio.mini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by jpc on 1/20/15.
 */
@Table(name = "binder_actors")
@Entity
@JsonIgnoreProperties({"binder", "actor"})
public class Party {
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
    private boolean opponent;

    public Party() {
    }

    public Party(Binder binder, Actor actor, boolean opponent) {
        this.binder = binder;
        this.actor = actor;
        this.opponent = opponent;
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

    public boolean isOpponent() {
        return opponent;
    }

    public void setOpponent(boolean opponent) {
        this.opponent = opponent;
    }
}
