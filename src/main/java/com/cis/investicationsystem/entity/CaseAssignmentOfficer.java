package com.cis.investicationsystem.entity;


import jakarta.persistence.*;

@Entity
@Table(name="case_investication_officer")
public class CaseAssignmentOfficer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assignmentId;
    private int caseId;
    private String officerId;

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }
}
