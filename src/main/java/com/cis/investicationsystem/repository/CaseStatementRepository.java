package com.cis.investicationsystem.repository;

import com.cis.investicationsystem.entity.CaseStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaseStatementRepository extends JpaRepository<CaseStatement, Integer> {

    @Query("SELECT cs FROM CaseStatement cs WHERE cs.court.caseNo = :caseNo")
    List<CaseStatement> findByCaseNo(@Param("caseNo") int caseNo);

}
