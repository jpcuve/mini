package com.messio.mini.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.messio.mini.DecisionLevel;
import com.messio.mini.DecisionWinner;
import com.messio.mini.RecordNature;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "decisions", catalog = "mini", uniqueConstraints = @UniqueConstraint(columnNames = { "reference" }))
@Entity
@JsonIgnoreProperties({"docket"})
public class Decision {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "reference", nullable = false, length = 127)
    private String reference;
    @Basic
    @Column(name = "level", nullable = false)
    private DecisionLevel level;
    @Basic
    @Column(name = "judgment_date", nullable = false)
    private String judgmentDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "record_nature", nullable = false, length = 255)
    private RecordNature recordNature;
    @Basic
    @Column(name = "refusal")
    private boolean refusal;
    @Basic
    @Column(name = "citable")
    private boolean citable;
    @Enumerated(EnumType.STRING)
    @Column(name = "winner")
    private DecisionWinner winner;
    @ManyToOne
    @JoinColumn(name = "docket_id")
    private Docket docket;

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

    public DecisionLevel getLevel() {
        return level;
    }

    public void setLevel(DecisionLevel level) {
        this.level = level;
    }

    public LocalDate getJudgmentDate(){
        return judgmentDate == null ? null : LocalDate.parse(judgmentDate);
    }

    public void setJudgmentDate(LocalDate localDate){
        this.judgmentDate = localDate == null ? null : localDate.toString();
    }

    public RecordNature getRecordNature() {
        return recordNature;
    }

    public void setRecordNature(RecordNature recordNature) {
        this.recordNature = recordNature;
    }

    public boolean isRefusal() {
        return refusal;
    }

    public void setRefusal(boolean refusal) {
        this.refusal = refusal;
    }

    public boolean isCitable() {
        return citable;
    }

    public void setCitable(boolean citable) {
        this.citable = citable;
    }

    public DecisionWinner getWinner() {
        return winner;
    }

    public void setWinner(DecisionWinner winner) {
        this.winner = winner;
    }

    public Docket getDocket() {
        return docket;
    }

    public void setDocket(Docket docket) {
        this.docket = docket;
    }
}
