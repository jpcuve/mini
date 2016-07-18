package com.messio.mini.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by jpc on 1/20/15.
 */
@Table(name = "binder_members", catalog = "mini")
@Entity
@JsonIgnoreProperties({"binder", "member"})
public class Party {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "binder_id")
    private Binder binder;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Basic
    private boolean opponent;

    public Party() {
    }

    public Party(Binder binder, Member member, boolean opponent) {
        this.binder = binder;
        this.member = member;
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public boolean isOpponent() {
        return opponent;
    }

    public void setOpponent(boolean opponent) {
        this.opponent = opponent;
    }
}
