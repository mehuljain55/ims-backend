package com.cis.investicationsystem.entity;

import com.cis.investicationsystem.entity.enums.CaseStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "court")
public class Court {

    @Id
    private int caseNo;

    private String description;

    @Enumerated(EnumType.STRING)
    private CaseStatus caseStatus;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CaseStatement> caseStatementList;

    public int getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(int caseNo) {
        this.caseNo = caseNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CaseStatus getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(CaseStatus caseStatus) {
        this.caseStatus = caseStatus;
    }

    public List<CaseStatement> getCaseStatementList() {
        return caseStatementList;
    }

    public void setCaseStatementList(List<CaseStatement> caseStatementList) {
        this.caseStatementList = caseStatementList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
