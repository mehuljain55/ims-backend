package com.cis.investicationsystem.entity;

import com.cis.investicationsystem.entity.enums.CaseStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cases")
public class Cases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int caseId;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.DATE)
    private Date caseRegisterDate;
    private String location;

    @Enumerated(EnumType.STRING)
    private CaseStatus caseStatus;

    private String status;

    @OneToMany(mappedBy = "cases", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Evidences> evidencesList;

    public Cases() {
    }

    public Cases(int caseId, String title, String description, String location, CaseStatus caseStatus) {
        this.caseId = caseId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.caseStatus = caseStatus;
    }


    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCaseRegisterDate() {
        return caseRegisterDate;
    }

    public void setCaseRegisterDate(Date caseRegisterDate) {
        this.caseRegisterDate = caseRegisterDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CaseStatus getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(CaseStatus caseStatus) {
        this.caseStatus = caseStatus;
    }

    public List<Evidences> getEvidencesList() {
        return evidencesList;
    }

    public void setEvidencesList(List<Evidences> evidencesList) {
        this.evidencesList = evidencesList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
