package com.messio.mini.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Created by jpc on 10/15/14.
 */
@Table(name = "binders")
@Entity
@NamedQueries({
        @NamedQuery(name = Binder.BINDER_IDS_BY_ANY_REFERENCE, query = "select distinct d.docket.binder.id from Decision d where d.reference like :reference or d.docket.reference like :reference or d.docket.binder.reference like :reference"),
        @NamedQuery(name = Binder.BINDER_BY_IDS, query = "select distinct b.id, b from Binder b where b.id in (:ids)"),
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
    @Column(name = "area")
    private Area area;
    @Enumerated(EnumType.STRING)
    @Column(name = "first_action")
    private FirstAction firstAction;
    @Basic
    @Column(name = "first_action_date")
    private String firstActionDate;
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

    public Binder(String reference, Area area, Set<Domain> domains) {
        this.reference = reference;
        this.area = area;
        this.domains = domains;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public FirstAction getFirstAction() {
        return firstAction;
    }

    public void setFirstAction(FirstAction firstAction) {
        this.firstAction = firstAction;
    }

    public LocalDate getFirstActionDate() {
        return firstActionDate == null ? null : LocalDate.parse(firstActionDate);
    }

    public void setFirstActionDate(LocalDate firstActionDate) {
        this.firstActionDate = firstActionDate == null ? null : firstActionDate.toString();
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
