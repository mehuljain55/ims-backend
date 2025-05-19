package com.cis.investicationsystem.entity.modals;

import com.cis.investicationsystem.entity.enums.EvidenceType;


public class EvidenceCollectionModal {

    private String description;
    private int caseId;
    private EvidenceType type;

    public EvidenceCollectionModal(String description, int caseId, EvidenceType type) {
        this.description = description;
        this.caseId = caseId;
        this.type = type;
    }

    public EvidenceCollectionModal() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public EvidenceType getType() {
        return type;
    }

    public void setType(EvidenceType type) {
        this.type = type;
    }
}
