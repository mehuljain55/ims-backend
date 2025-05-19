package com.cis.investicationsystem.entity;

import com.cis.investicationsystem.entity.enums.EvidenceSubType;
import com.cis.investicationsystem.entity.enums.EvidenceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "evidence")
public class Evidences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evidenceNo;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Date date;
    private String filePath;

    @Enumerated(EnumType.STRING)
    private EvidenceType type;

    @Enumerated(EnumType.STRING)
    private EvidenceSubType subType;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    @JsonIgnore
    private Cases cases;

    private String uploadedBy;
    private String officerName;

    public Evidences() {
    }

    public Evidences(int evidenceNo, String description, String filePath, EvidenceType type, Cases cases) {
        this.evidenceNo = evidenceNo;
        this.description = description;
        this.filePath = filePath;
        this.type = type;
        this.cases = cases;
    }


    public int getEvidenceNo() {
        return evidenceNo;
    }

    public void setEvidenceNo(int evidenceNo) {
        this.evidenceNo = evidenceNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public EvidenceType getType() {
        return type;
    }

    public void setType(EvidenceType type) {
        this.type = type;
    }

    public EvidenceSubType getSubType() {
        return subType;
    }

    public void setSubType(EvidenceSubType subType) {
        this.subType = subType;
    }

    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }
}
