package com.messio.mini.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by jpc on 10/15/14.
 */
@Table(name = "dockets", catalog = "mini", uniqueConstraints = @UniqueConstraint(columnNames = { "reference" }))
@Entity
@JsonIgnoreProperties({"binder", "court"})
public class Docket {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "reference", nullable = false, length = 127)
    private String reference;
    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;
    @ManyToOne
    @JoinColumn(name = "binder_id")
    private Binder binder;

    public Docket() {
    }

    public Docket(Binder binder, Court court, String reference) {
        this.binder = binder;
        this.court = court;
        this.reference = reference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Binder getBinder() {
        return binder;
    }

    public void setBinder(Binder binder) {
        this.binder = binder;
    }
}
