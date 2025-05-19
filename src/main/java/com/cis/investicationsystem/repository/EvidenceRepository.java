package com.cis.investicationsystem.repository;

import com.cis.investicationsystem.entity.Evidences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvidenceRepository extends JpaRepository<Evidences,Integer> {

    @Query("SELECT e FROM Evidences e WHERE e.cases.caseId = :caseId")
    List<Evidences> findEvidencesByCaseId(@Param("caseId") int caseId);
}
