package com.cis.investicationsystem.controller;

import com.cis.investicationsystem.entity.User;
import com.cis.investicationsystem.entity.modals.ApiResponseModel;
import com.cis.investicationsystem.service.CaseService;
import com.cis.investicationsystem.service.CourtService;
import com.cis.investicationsystem.service.UserAuthorizationService;
import com.cis.investicationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @Autowired
    private CourtService courtService;

    @PostMapping("/create")
    public ApiResponseModel createUser(@RequestBody User user)
    {
        return userAuthorizationService.addUser(user);
    }


    @GetMapping("/login")
    public ApiResponseModel login(@RequestParam("emailId") String emailId,
                                  @RequestParam("password")String password)
    {
        return userAuthorizationService.validateUserLogin(emailId,password);
    }

    @GetMapping("/view_verdict")
    public ApiResponseModel viewDecision(@RequestParam("caseNo") int caseNo)
    {
        return courtService.viewVerdict(caseNo);
    }

}
