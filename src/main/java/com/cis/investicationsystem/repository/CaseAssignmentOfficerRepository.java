package com.cis.investicationsystem.repository;

import com.cis.investicationsystem.entity.CaseAssignmentOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaseAssignmentOfficerRepository extends JpaRepository<CaseAssignmentOfficer,Integer> {

    @Query("SELECT c FROM CaseAssignmentOfficer c WHERE c.officerId = :officerId")
    List<CaseAssignmentOfficer> findCasesByOfficerId(@Param("officerId") String officerId);
}
