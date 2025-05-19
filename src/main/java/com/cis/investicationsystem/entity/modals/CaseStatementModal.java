package com.cis.investicationsystem.entity.modals;

public class CaseStatementModal {

    private int caseId;
    private int evidenceId;
    private String description;
    private String presentedBy;

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(int evidenceId) {
        this.evidenceId = evidenceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPresentedBy() {
        return presentedBy;
    }

    public void setPresentedBy(String presentedBy) {
        this.presentedBy = presentedBy;
    }
}
