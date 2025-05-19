package com.cis.investicationsystem.controller;

import com.cis.investicationsystem.entity.enums.StatusResponse;
import com.cis.investicationsystem.entity.enums.UserRoles;
import com.cis.investicationsystem.entity.modals.ApiRequestModel;
import com.cis.investicationsystem.entity.modals.ApiResponseModel;
import com.cis.investicationsystem.service.CaseService;
import com.cis.investicationsystem.service.CourtService;
import com.cis.investicationsystem.service.FileService;
import com.cis.investicationsystem.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/court")
public class CourtController {

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @Autowired
    private CaseService caseService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private FileService fileService;

    private final UserRoles roles=UserRoles.judge;


    @PostMapping("/findCaseList")
    public ApiResponseModel findCaseByOfficer(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return courtService.findCaseList();
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/findEvidenceList")
    public ApiResponseModel findEvidenceList(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return caseService.findEvidencesList(apiRequestModel.getCaseNo());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/add/statement")
    public ApiResponseModel caseStatement(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return courtService.addCaseEvidenceStatement(apiRequestModel.getCaseStatementModal());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/add/testimony")
    public ApiResponseModel addTestimonyStatement(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return courtService.addCaseTestimonyStatement(apiRequestModel.getCaseStatementModal());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/find/statement")
    public ApiResponseModel findCaseStatement(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return courtService.findCaseStatement(apiRequestModel.getCaseNo());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadEvidence(@RequestParam String filePath) {
        try {
            byte[] fileContent = fileService.getEvidenceFile(filePath);
            String fileName = Path.of(filePath).getFileName().toString();

            String mimeType = Files.probeContentType(Path.of(filePath));
            if (mimeType == null) {
                mimeType = "application/octet-stream"; // fallback if unknown
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/verdict")
    public ApiResponseModel addVerdict(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return courtService.addCaseVerdict(apiRequestModel.getCaseVerdictModal());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }
}
