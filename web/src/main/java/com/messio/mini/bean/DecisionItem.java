package com.messio.mini.bean;

import com.messio.mini.entity.Decision;

import java.util.List;

/**
 * Created by jpc on 11/10/14.
 */
public class DecisionItem {
    private Decision decision;
    private List<DocumentItem> documentItems;
    private List<AnalysisItem> analysisItems;
    private List<HonorItem> honorItems;

    public DecisionItem() {
    }

    public DecisionItem(Decision decision) {
        this.decision = decision;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public List<DocumentItem> getDocumentItems() {
        return documentItems;
    }

    public void setDocumentItems(List<DocumentItem> documentItems) {
        this.documentItems = documentItems;
    }

    public List<AnalysisItem> getAnalysisItems() {
        return analysisItems;
    }

    public void setAnalysisItems(List<AnalysisItem> analysisItems) {
        this.analysisItems = analysisItems;
    }

    public List<HonorItem> getHonorItems() {
        return honorItems;
    }

    public void setHonorItems(List<HonorItem> honorItems) {
        this.honorItems = honorItems;
    }
}
