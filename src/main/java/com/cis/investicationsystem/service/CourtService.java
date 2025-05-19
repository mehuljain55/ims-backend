package com.cis.investicationsystem.service;

import com.cis.investicationsystem.entity.CaseStatement;
import com.cis.investicationsystem.entity.Cases;
import com.cis.investicationsystem.entity.Court;
import com.cis.investicationsystem.entity.Evidences;
import com.cis.investicationsystem.entity.enums.CaseStatus;
import com.cis.investicationsystem.entity.enums.StatementType;
import com.cis.investicationsystem.entity.enums.StatusResponse;
import com.cis.investicationsystem.entity.modals.ApiResponseModel;
import com.cis.investicationsystem.entity.modals.CaseStatementModal;
import com.cis.investicationsystem.entity.modals.CaseVerdictModal;
import com.cis.investicationsystem.repository.CaseStatementRepository;
import com.cis.investicationsystem.repository.CasesRepository;
import com.cis.investicationsystem.repository.CourtRepository;
import com.cis.investicationsystem.repository.EvidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourtService {

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private CasesRepository casesRepository;

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private CaseStatementRepository caseStatementRepository;

    public ApiResponseModel findCaseList()
    {
        List<Court> caseList=courtRepository.findAll();
        List<Cases> courtCaseList=new ArrayList<>();

        for(Court caseInfo:caseList)
        {
            Optional<Cases> casesOptional=casesRepository.findById(caseInfo.getCaseNo());

            if(casesOptional.isPresent())
            {
                Cases cases=casesOptional.get();
                cases.setCaseStatus(caseInfo.getCaseStatus());
                courtCaseList.add(cases);
            }
        }

        if(courtCaseList.size()>0)
        {
            return new ApiResponseModel<>(StatusResponse.success,courtCaseList,"Case found");
        }else {
            return new ApiResponseModel<>(StatusResponse.failed,null,"No cases found");
        }
    }

    public ApiResponseModel addCaseEvidenceStatement(CaseStatementModal caseStatementModal)
    {
        Optional<Court> casesOptional=courtRepository.findById(caseStatementModal.getCaseId());

        if(casesOptional.isPresent())
        {
           Court court=findCase(caseStatementModal.getCaseId());

           if(court==null)
           {
               return new ApiResponseModel<>(StatusResponse.failed,null,"Invalid case id");
           }

           Optional<Evidences> optionalEvidences=evidenceRepository.findById(caseStatementModal.getEvidenceId());

           Evidences evidences=optionalEvidences.get();

           CaseStatement caseStatement=new CaseStatement();
           caseStatement.setCourt(court);
           caseStatement.setDescription(caseStatementModal.getDescription());
           caseStatement.setType(StatementType.evidence);
           caseStatement.setPresentedBy(evidences.getOfficerName());
           caseStatementRepository.save(caseStatement);
           return new ApiResponseModel<>(StatusResponse.success,null,"Case statement saved");

        }else {
            return new ApiResponseModel<>(StatusResponse.failed,null,"Unable to save evidence");
            }
    }

    public ApiResponseModel addCaseTestimonyStatement(CaseStatementModal caseStatementModal)
    {
        System.out.println(caseStatementModal.getCaseId());
        System.out.println(caseStatementModal.getPresentedBy());
        Optional<Court> casesOptional=courtRepository.findById(caseStatementModal.getCaseId());

        if(casesOptional.isPresent())
        {
            Court court=findCase(caseStatementModal.getCaseId());

            if(court==null)
            {
                return new ApiResponseModel<>(StatusResponse.failed,null,"Invalid case id");
            }

            CaseStatement caseStatement=new CaseStatement();
            caseStatement.setCourt(court);
            caseStatement.setDescription(caseStatementModal.getDescription());
            caseStatement.setType(StatementType.testimony);
            caseStatement.setPresentedBy(caseStatementModal.getPresentedBy());
            caseStatementRepository.save(caseStatement);
            return new ApiResponseModel<>(StatusResponse.success,null,"Case statement saved");

        }else {
            return new ApiResponseModel<>(StatusResponse.failed,null,"Unable to save evidence");
        }


    }


    public ApiResponseModel addCaseVerdict(CaseVerdictModal caseVerdictModal) {
        Optional<Court> courtCaseOptional = courtRepository.findById(caseVerdictModal.getCaseId());

        if (courtCaseOptional.isPresent()) {
            Optional<Cases> casesOptional=casesRepository.findById(caseVerdictModal.getCaseId());

           Court court=courtCaseOptional.get();
           court.setDescription(caseVerdictModal.getDescription());
           court.setCaseStatus(CaseStatus.closed);


            if(casesOptional.isPresent())
            {
                Cases cases=casesOptional.get();
                cases.setCaseStatus(CaseStatus.closed);
                casesRepository.save(cases);
            }

           courtRepository.save(court);

        }
        return new ApiResponseModel<>(StatusResponse.success,null,"Verdict added");
    }

    public ApiResponseModel findCaseStatement(int caseId)
    {
        List<CaseStatement> caseStatementList=caseStatementRepository.findByCaseNo(caseId);
        if(caseStatementList.size()>0)
        {
            return new ApiResponseModel<>(StatusResponse.success,caseStatementList,"Case statement found");
        }else {
            return new ApiResponseModel<>(StatusResponse.failed,null,"No statement found");
        }

    }

    public ApiResponseModel viewVerdict(int caseId)
    {
        Court court=findCase(caseId);

        if(court!=null)
        {
            String description=court.getDescription();
            if(court.getCaseStatus().equals(CaseStatus.opened))
            {
                description="Case is pending";
                return new ApiResponseModel<>(StatusResponse.success,description,"Case is pending");
            }else {
                return new ApiResponseModel<>(StatusResponse.success,description,"Verdict found");

            }

        }else {
            return new ApiResponseModel<>(StatusResponse.success,null,"Case not found");
        }

    }

    private Court findCase(int caseId)
    {
        Optional<Court> casesOptional=courtRepository.findById(caseId);

        if(casesOptional.isPresent())
        {
            Court court=casesOptional.get();
            return court;
        }else {
            return null;
        }
    }

}
