package com.cis.investicationsystem.entity.modals;

import com.cis.investicationsystem.entity.Cases;
import com.cis.investicationsystem.entity.User;

import java.util.List;

public class ApiRequestModel {

    private User user;
    private String token;
    private String emailId;
    private int caseNo;
    private int evidenceNo;
    private Cases cases;
    private EvidenceCollectionModal evidenceCollectionModal;
    private List<CaseAssignmentModel> caseAssignmentList;
    private CaseStatementModal caseStatementModal;
    private CaseVerdictModal caseVerdictModal;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public int getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(int caseNo) {
        this.caseNo = caseNo;
    }

    public int getEvidenceNo() {
        return evidenceNo;
    }

    public void setEvidenceNo(int evidenceNo) {
        this.evidenceNo = evidenceNo;
    }

    public EvidenceCollectionModal getEvidenceCollectionModal() {
        return evidenceCollectionModal;
    }

    public void setEvidenceCollectionModal(EvidenceCollectionModal evidenceCollectionModal) {
        this.evidenceCollectionModal = evidenceCollectionModal;
    }

    public List<CaseAssignmentModel> getCaseAssignmentList() {
        return caseAssignmentList;
    }

    public void setCaseAssignmentList(List<CaseAssignmentModel> caseAssignmentList) {
        this.caseAssignmentList = caseAssignmentList;
    }

    public CaseStatementModal getCaseStatementModal() {
        return caseStatementModal;
    }

    public void setCaseStatementModal(CaseStatementModal caseStatementModal) {
        this.caseStatementModal = caseStatementModal;
    }

    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public CaseVerdictModal getCaseVerdictModal() {
        return caseVerdictModal;
    }

    public void setCaseVerdictModal(CaseVerdictModal caseVerdictModal) {
        this.caseVerdictModal = caseVerdictModal;
    }
}
