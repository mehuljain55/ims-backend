package com.cis.investicationsystem.repository;

import com.cis.investicationsystem.entity.User;
import com.cis.investicationsystem.entity.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findUsersByRole(UserRoles role);

}
