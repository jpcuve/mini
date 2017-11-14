package com.messio.mini.domain;

import javax.persistence.*;
import java.util.Locale;

/**
 * Created by jpc on 10/10/14.
 */
@Table(name = "documents", uniqueConstraints = @UniqueConstraint(columnNames = { "lang", "decision_id" }))
@Entity
public class Document {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "lang", nullable = false, length = 127)
    private String lang;
    @Basic
    @Column(name = "document_id", nullable = false, length = 63)
    private String documentId;
    @ManyToOne
    @JoinColumn(name = "decision_id")
    private Decision decision;

    public Document() {
    }

    public Document(Decision decision, Locale locale, String documentId) {
        this.decision = decision;
        setLocale(locale);
        this.documentId = documentId;
    }

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
