package com.messio.mini.domain;


import javax.persistence.*;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "actors")
@Entity
@NamedQueries({
        @NamedQuery(name = Actor.ACTOR_BY_IDS, query = "select distinct a.id, a from Actor a where a.id in (:ids)"),
})
public class Actor {
    public static final String ACTOR_BY_IDS = "actorByIds";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;

    public Actor() {
    }

    public Actor(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
