package com.cis.investicationsystem.entity;

import com.cis.investicationsystem.entity.enums.StatementType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "case_statement")
public class CaseStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sno;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatementType type;

    @ManyToOne
    @JoinColumn(name = "court_id")
    @JsonBackReference
    private Court court;

    private String presentedBy;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatementType getType() {
        return type;
    }

    public void setType(StatementType type) {
        this.type = type;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public String getPresentedBy() {
        return presentedBy;
    }

    public void setPresentedBy(String presentedBy) {
        this.presentedBy = presentedBy;
    }
}
