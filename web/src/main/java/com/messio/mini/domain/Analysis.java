package com.messio.mini.domain;

import javax.persistence.*;

/**
 * Created by jpc on 1/20/15.
 */
@Table(name = "analyses")
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
}
