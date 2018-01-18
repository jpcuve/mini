package com.messio.mini.domain;

import javax.persistence.*;

/**
 * Created by jpc on 1/20/15.
 */
@Table(name = "analyses", uniqueConstraints = { @UniqueConstraint(columnNames = {"decision_id", "pol_id"})})
@Entity
public class Analysis {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "decision_id")
    private Decision decision;
    @ManyToOne
    @JoinColumn(name = "pol_id")
    private Pol pol;
    @Basic
    @Column(name = "decision_id", insertable = false, updatable = false, nullable = false)
    private Long decisionId;
    @Basic
    @Column(name = "pol_id", insertable = false, updatable = false, nullable = false)
    private Long polId;


    public Analysis() {
    }

    public Analysis(Decision decision, Pol pol) {
        this.decision = decision;
        this.pol = pol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public Pol getPol() {
        return pol;
    }

    public void setPol(Pol pol) {
        this.pol = pol;
    }

    public Long getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(Long decisionId) {
        this.decisionId = decisionId;
    }

    public Long getPolId() {
        return polId;
    }

    public void setPolId(Long polId) {
        this.polId = polId;
    }
}
