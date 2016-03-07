package com.messio.mini.bean;

import com.messio.mini.entity.Document;

/**
 * Created by jpc on 30/10/14.
 */
public class DocumentItem {
    private Document document;

    public DocumentItem() {
    }

    public DocumentItem(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
