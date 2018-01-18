package com.messio.mini.domain;

import javax.persistence.*;

/**
 * Created by jpc on 30/01/15.
 */
@Table(name = "honors", uniqueConstraints = @UniqueConstraint(columnNames = { "decision_id", "right_id" }))
@Entity
public class Honor {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "decision_id")
    private Decision decision;
    @ManyToOne
    @JoinColumn(name = "right_id")
    private Right right;
    @Column(name = "validity")
    @Enumerated(EnumType.STRING)
    private RightValidity rightValidity;
    @Basic
    @Column(name = "decision_id", insertable = false, updatable = false, nullable = false)
    private Long decisionId;
    @Basic
    @Column(name = "right_id", insertable = false, updatable = false, nullable = false)
    private Long rightId;

    public Honor() {
    }

    public Honor(Decision decision, Right right, RightValidity rightValidity) {
        this.decision = decision;
        this.right = right;
        this.rightValidity = rightValidity;
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

    public Right getRight() {
        return right;
    }

    public void setRight(Right right) {
        this.right = right;
    }

    public RightValidity getRightValidity() {
        return rightValidity;
    }

    public void setRightValidity(RightValidity rightValidity) {
        this.rightValidity = rightValidity;
    }

    public Long getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(Long decisionId) {
        this.decisionId = decisionId;
    }

    public Long getRightId() {
        return rightId;
    }

    public void setRightId(Long rightId) {
        this.rightId = rightId;
    }
}
