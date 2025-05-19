package com.cis.investicationsystem.controller;

import com.cis.investicationsystem.entity.User;
import com.cis.investicationsystem.entity.enums.StatusResponse;
import com.cis.investicationsystem.entity.enums.UserRoles;
import com.cis.investicationsystem.entity.modals.ApiRequestModel;
import com.cis.investicationsystem.entity.modals.ApiResponseModel;
import com.cis.investicationsystem.service.CaseService;
import com.cis.investicationsystem.service.UserAuthorizationService;
import com.cis.investicationsystem.service.FileService;
import com.cis.investicationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @Autowired
    private CaseService caseService;

    @Autowired
    private FileService fileService;

    private final  UserRoles roles=UserRoles.admin;


    @PostMapping("/case/register")
    public ApiResponseModel register(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return caseService.createCaseDetail(apiRequestModel.getCases());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/find/officerList")
    public ApiResponseModel officerList(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return userService.findUserList();
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/findAllCases")
    public ApiResponseModel findAllCases(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return caseService.findAllCases();
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

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadEvidence(@RequestParam String filePath) {
        try {
            byte[] fileContent = fileService.getEvidenceFile(filePath);
            String fileName = Path.of(filePath).getFileName().toString();

            // Determine MIME type dynamically
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


    @PostMapping("/allot/caseOfficer")
    public ApiResponseModel allotCaseOfficer(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return caseService.caseOfficerAllotment(apiRequestModel.getCaseAssignmentList());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @PostMapping("/transfer/sendCaseToCourt")
    public ApiResponseModel sendCaseToCourt(@RequestBody ApiRequestModel apiRequestModel)
    {
        boolean status=userAuthorizationService.validateUserAccess(apiRequestModel.getUser(),apiRequestModel.getToken(), roles);

        if(status)
        {
            return caseService.sendCaseToCourt(apiRequestModel.getCaseNo());
        }else {
            return new ApiResponseModel<>(StatusResponse.unauthorized,null,"Unauthorized access");
        }
    }

    @GetMapping("/video/watch")
    public ResponseEntity<Resource> streamVideo(
            @RequestParam("filePath") String filePath,
            @RequestHeader(value = "Range", required = false) String rangeHeader) {
        try {
            return fileService.getChunkedVideo(filePath, rangeHeader);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
