package com.cis.investicationsystem.repository;

import com.cis.investicationsystem.entity.Cases;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasesRepository extends JpaRepository<Cases,Integer> {
}
