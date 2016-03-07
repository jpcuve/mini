package com.messio.mini.entity;

import javax.persistence.*;
import java.util.Locale;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "documents", catalog = "mini", uniqueConstraints = @UniqueConstraint(columnNames = { "lang", "decision_id" }))
@Entity
public class Document {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "reference", nullable = true, length = 127)
    private String reference;
    @Basic
    @Column(name = "lang", nullable = false, length = 127)
    private String lang;
    @Basic
    @Column(name = "document_id", nullable = false, length = 63)
    private String documentId;
    @ManyToOne
    @JoinColumn(name = "decision_id")
    private Decision decision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLocale(Locale locale){
        this.lang = locale == null ? null : locale.toLanguageTag();
    }

    public Locale getLocale(){
        return lang == null ? null : Locale.forLanguageTag(lang);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }
}
