package com.messio.mini.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jpc on 10/15/14.
 */
@Table(name = "dockets", catalog = "mini", uniqueConstraints = @UniqueConstraint(columnNames = { "reference" }))
@Entity
@NamedQueries({
        @NamedQuery(name = Docket.DOCKET_ALL, query = "select o from Docket o"),
        @NamedQuery(name = Docket.DOCKET_BY_IDS, query = "select distinct o from Docket o where o.id in (:ids)"),
        @NamedQuery(name = Docket.DOCKET_BY_BINDER_IDS, query = "select distinct o from Docket o where o.binder.id in (:ids)")
})
@JsonIgnoreProperties({"binder", "court"})
public class Docket {
    public static final String DOCKET_ALL = "docket.all";
    public static final String DOCKET_BY_IDS = "docket.byIds";
    public static final String DOCKET_BY_BINDER_IDS = "docket.byBinderIds";
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
    @Basic
    @Column(name = "court_id", insertable = false, updatable = false)
    private Long courtId;
    @Basic
    @Column(name = "binder_id", insertable = false, updatable = false)
    private Long binderId;

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

    public Long getCourtId() {
        return courtId;
    }

    public Long getBinderId() {
        return binderId;
    }
}
