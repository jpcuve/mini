package com.messio.mini.bean;

import com.messio.mini.entity.Analysis;

/**
 * Created by jpc on 24/01/15.
 */
public class AnalysisItem {
    private Analysis analysis;

    public AnalysisItem(Analysis analysis) {
        this.analysis = analysis;
    }

    public AnalysisItem() {
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }
}
