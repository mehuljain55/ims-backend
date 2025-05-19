package com.cis.investicationsystem.service;

import com.cis.investicationsystem.entity.*;
import com.cis.investicationsystem.entity.enums.CaseStatus;
import com.cis.investicationsystem.entity.enums.StatusResponse;
import com.cis.investicationsystem.entity.modals.ApiResponseModel;
import com.cis.investicationsystem.entity.modals.CaseAssignmentModel;
import com.cis.investicationsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CaseService {

    @Autowired
    private CasesRepository casesRepository;

    @Autowired
    private CaseAssignmentOfficerRepository caseAssignmentOfficerRepository;

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourtRepository courtRepository;


    public ApiResponseModel createCaseDetail(Cases cases)
    {

        try {
            cases.setCaseRegisterDate(new Date());
            cases.setCaseStatus(CaseStatus.opened);
            cases.setStatus("Case is registered and investigation going on");
            casesRepository.save(cases);
            return new ApiResponseModel<>(StatusResponse.success,null,"Case registered");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ApiResponseModel<>(StatusResponse.failed,null,"Unable to register case detail");
        }
    }

    public ApiResponseModel sendCaseToCourt(int caseId)
    {

        try {
          Optional<Cases> casesOptional=casesRepository.findById(caseId);

          if(casesOptional.isPresent()) {

              Optional<Court> courtCaseOptional =courtRepository.findById(caseId);

              if(courtCaseOptional.isPresent())
              {
                  return new ApiResponseModel<>(StatusResponse.failed,null,"Case already in hearing");
              }


              Cases cases= casesOptional.get();
              cases.setStatus("Case is send to court");

              Court court=new Court();
              court.setCaseNo(cases.getCaseId());
              court.setDate(new Date());
              court.setCaseStatus(CaseStatus.opened);



              casesRepository.save(cases);
              courtRepository.save(court);

              return new ApiResponseModel<>(StatusResponse.success, null, "Case registered");
          }else {
              return new ApiResponseModel<>(StatusResponse.failed,null,"Invalid case id");
          }
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ApiResponseModel<>(StatusResponse.failed,null,"Unable to send case detail");
        }
    }

    public ApiResponseModel findAllCases()
    {
        List<Cases> caseList=casesRepository.findAll();

        if(caseList.size()>0)
        {
            return new ApiResponseModel<>(StatusResponse.success,caseList,"Case list found");
        }else {
            return new ApiResponseModel<>(StatusResponse.not_found,null,"No cases found");
        }
    }

    public ApiResponseModel findEvidencesList(int caseNo)
    {
          List<Evidences> evidencesList=evidenceRepository.findEvidencesByCaseId(caseNo);
          if(evidencesList.size()>0)
          {
              return new ApiResponseModel<>(StatusResponse.success,evidencesList,"Evidence List found");
          }else {
              return new ApiResponseModel<>(StatusResponse.not_found,null,"No cases found");
          }
    }

    public ApiResponseModel findCasesByOfficerId(String officerId)
    {
        List<CaseAssignmentOfficer> caseOffiers=caseAssignmentOfficerRepository.findCasesByOfficerId(officerId);
        List<Cases> caseList=new ArrayList<>();

        for(CaseAssignmentOfficer caseAssignmentOfficer:caseOffiers)
        {
            Cases cases=findCaseByCaseId(caseAssignmentOfficer.getCaseId());
            if(cases!=null)
            {
                caseList.add(cases);
            }

        }

        if(caseList.size()>0)
        {
            return new ApiResponseModel<>(StatusResponse.success,caseList,"Case list found");
        }else {
            return new ApiResponseModel<>(StatusResponse.not_found,null,"No cases found");
        }
    }

    public ApiResponseModel caseOfficerAllotment(List<CaseAssignmentModel> allotmentList)
    {
        for(CaseAssignmentModel caseAssignmentModel:allotmentList)
        {
            Optional<User> userOptional=userRepository.findById(caseAssignmentModel.getOfficerId());

            if(userOptional.isPresent())
            {
                Cases cases=findCaseByCaseId(caseAssignmentModel.getCaseNo());
                if(cases==null)
                {
                    continue;
                }
                CaseAssignmentOfficer caseAssignmentOfficer=new CaseAssignmentOfficer();
                caseAssignmentOfficer.setCaseId(cases.getCaseId());
                caseAssignmentOfficer.setOfficerId(caseAssignmentModel.getOfficerId());
                caseAssignmentOfficerRepository.save(caseAssignmentOfficer);
            }
        }
        return new ApiResponseModel(StatusResponse.success,null,"Allotment Success");
    }


    private Cases findCaseByCaseId(int caseNo)
    {
        Optional<Cases> caseOptional=casesRepository.findById(caseNo);
        if(caseOptional.isPresent())
        {
            Cases cases=caseOptional.get();
            return cases;
        }else {
            return null;
        }
    }
}
