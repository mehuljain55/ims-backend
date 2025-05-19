package com.cis.investicationsystem.controller;

import com.cis.investicationsystem.entity.enums.EvidenceSubType;
import com.cis.investicationsystem.entity.enums.StatusResponse;
import com.cis.investicationsystem.entity.enums.UserRoles;
import com.cis.investicationsystem.entity.modals.ApiRequestModel;
import com.cis.investicationsystem.entity.modals.ApiResponseModel;
import com.cis.investicationsystem.service.CaseService;
import com.cis.investicationsystem.service.EvidenceService;
import com.cis.investicationsystem.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/investigator")
public class InvestigatorController {

    @Autowired
    private UserAuthorizationService userAuthorizationService;


    @Autowired
    private CaseService caseService;

    @Autowired
    private EvidenceService evidenceService;

    private final UserRoles roles=UserRoles.investigator;

    @PostMapping("/findCaseList")
    public ApiResponseModel findCaseByOfficer(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return caseService.findCasesByOfficerId(apiRequestModel.getUser().getEmailId());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/saveEvidence")
    public ApiResponseModel<?> saveEvidence(
            @RequestPart("requestBody") ApiRequestModel apiRequestModel,
            @RequestPart("file") MultipartFile file) throws IOException {

        boolean status = userAuthorizationService.validateUserAccess(
                apiRequestModel.getUser(),
                apiRequestModel.getToken(),
                roles);

        if (status) {
            return evidenceService.saveEvidence(
                    apiRequestModel.getEvidenceCollectionModal(),
                    EvidenceSubType.investigation_report,
                    apiRequestModel.getUser().getEmailId(),
                    apiRequestModel.getUser().getName(),
                    file);
        } else {
            return new ApiResponseModel<>(StatusResponse.unauthorized, null, "Unauthorized access");
        }
    }

 }


