package com.cis.investicationsystem.service;

import com.cis.investicationsystem.entity.Cases;
import com.cis.investicationsystem.entity.Evidences;
import com.cis.investicationsystem.entity.enums.EvidenceSubType;
import com.cis.investicationsystem.entity.enums.StatusResponse;
import com.cis.investicationsystem.entity.modals.ApiResponseModel;
import com.cis.investicationsystem.entity.modals.EvidenceCollectionModal;
import com.cis.investicationsystem.repository.CasesRepository;
import com.cis.investicationsystem.repository.EvidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@Service
public class EvidenceService {

    @Autowired
    private CasesRepository casesRepository;

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private FileUploadServiceLocal fileUploadServiceLocal;


    public ApiResponseModel saveEvidence(EvidenceCollectionModal evidenceCollectionModal, EvidenceSubType subType, String officerId,String officerName, MultipartFile file) throws IOException {
        Optional<Cases> casesOptional=casesRepository.findById(evidenceCollectionModal.getCaseId());

        if(casesOptional.isPresent())
        {
            Cases cases=casesOptional.get();

            Evidences evidences=new Evidences();
            evidences.setCases(cases);
            evidences.setDate(new Date());
            evidences.setDescription(evidenceCollectionModal.getDescription());
            evidences.setType(evidenceCollectionModal.getType());
            evidences.setOfficerName(officerName);
            evidences.setSubType(subType);
            evidences.setUploadedBy(officerId);
            Evidences saveEvidence=evidenceRepository.save(evidences);

            try {


                if (file != null) {
                    String folderName = cases.getCaseId() + "";
                    String fileName = evidences.getEvidenceNo() + "";
                    String url = fileUploadServiceLocal.uploadFile(file, folderName, fileName);
                    saveEvidence.setFilePath(url);
                    evidenceRepository.save(saveEvidence);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return new ApiResponseModel<>(StatusResponse.success,null,"Evidence saved");

        }else {
            return new ApiResponseModel<>(StatusResponse.failed,null,"Invalid case number");
        }

    }


    public byte[] getFileBytes(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("File not found: " + filePath);
        }
        return Files.readAllBytes(path);
    }


}
