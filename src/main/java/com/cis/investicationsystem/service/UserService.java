package com.cis.investicationsystem.service;

import com.cis.investicationsystem.entity.User;
import com.cis.investicationsystem.entity.enums.StatusResponse;
import com.cis.investicationsystem.entity.enums.UserRoles;
import com.cis.investicationsystem.entity.modals.ApiResponseModel;
import com.cis.investicationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ApiResponseModel findUserList()
    {
        List<User> investigator=userRepository.findUsersByRole(UserRoles.investigator);
        List<User> forencis=userRepository.findUsersByRole(UserRoles.forensic);
        List<User> officerList=new ArrayList<>();
        officerList.addAll(investigator);
        officerList.addAll(forencis);

        if(officerList.size()>0)
        {
            return new ApiResponseModel<>(StatusResponse.success,officerList,"Officer list");
        }else {
            return new ApiResponseModel<>(StatusResponse.not_found,null,"No user found");
        }
    }
}
