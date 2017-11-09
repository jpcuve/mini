package com.messio.mini.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by jpc on 10/15/14.
 */
@Table(name = "binders")
@Entity
@NamedQueries({
        @NamedQuery(name = Binder.BINDER_IDS_BY_ANY_REFERENCE, query = "select distinct d.docket.binder.id from Decision d where d.reference like :reference or d.docket.reference like :reference or d.docket.binder.reference like :reference"),
        @NamedQuery(name = Binder.BINDER_BY_IDS, query = "select distinct b from Binder b where b.id in (:ids)"),
        @NamedQuery(name = Binder.BINDER_COUNT_IDS, query = "select count(b.id) from Binder b")
})
public class Binder {
    public static final String BINDER_BY_IDS = "binder.byIds";
    public static final String BINDER_IDS_BY_ANY_REFERENCE = "binder.idsByAnyReference";
    public static final String BINDER_COUNT_IDS = "binder.countIds";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "reference", nullable = false)
    private String reference;
    @Enumerated(EnumType.STRING)
    @Column(name = "first_action")
    private FirstAction firstAction;
    @Basic
    @Column(name = "withdrawal")
    private boolean withdrawal;
    @Basic
    @Column(name = "settled")
    private boolean settled;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "binder_domains", joinColumns = { @JoinColumn(name = "binder_id")})
    @Enumerated(EnumType.STRING)
    @Column(name = "domain")
    private Set<Domain> domains;

    public Binder() {
    }

    public Binder(String reference, FirstAction firstAction) {
        this.reference = reference;
        this.firstAction = firstAction;
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

    public FirstAction getFirstAction() {
        return firstAction;
    }

    public void setFirstAction(FirstAction firstAction) {
        this.firstAction = firstAction;
    }

    public boolean isWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(boolean withdrawal) {
        this.withdrawal = withdrawal;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    public Set<Domain> getDomains() {
        return domains;
    }

    public void setDomains(Set<Domain> domains) {
        this.domains = domains;
    }
}
