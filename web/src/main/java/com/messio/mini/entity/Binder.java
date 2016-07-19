package com.messio.mini.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by jpc on 10/15/14.
 */
@Table(name = "binders", catalog = "mini")
@Entity
@NamedQueries({
        @NamedQuery(name = "binder.bindersByTrademark", query = "select distinct b from Binder b join b.rights r join r.trademark t where t.name like :trademark"),
        @NamedQuery(name = Binder.BINDER_BY_IDS, query = "select distinct b from Binder b where b.id in (:ids)")
})
public class Binder {
    public static final String BINDER_BY_IDS = "binder.byIds";
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "reference", nullable = false, length = 127)
    private String reference;
    @Enumerated(EnumType.STRING)
    @Column(name = "first_action", nullable = true, length = 127)
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
