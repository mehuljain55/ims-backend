package com.cis.investicationsystem.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadServiceLocal {

    private static final String BASE_PATH = "C:" + File.separator + "investication";


    public void createCourseSectionFolders(String folderName) throws IOException {

        String fullFolderPath = BASE_PATH + File.separator + folderName;
        Path folderPath = Paths.get(fullFolderPath);

        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

    }

    public String uploadFile(MultipartFile file, String folderName, String fileName) throws IOException {
        String fullFolderPath = BASE_PATH + File.separator + folderName;
        Path folderPath = Paths.get(fullFolderPath);

        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        String finalFileName = fileName + (fileExtension != null ? "." + fileExtension : "");
        Path targetPath = folderPath.resolve(finalFileName);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        String filePath = targetPath.toString();
        String fullFilePath = BASE_PATH + File.separator + folderName + File.separator + finalFileName;

        return fullFilePath;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public boolean deleteFile(String filePath) {
        Path path = Paths.get(filePath);

        try {
            if (Files.exists(path)) {
                Files.delete(path);  // Delete the file
                System.out.println("File deleted successfully.");
                return true;
            } else {
                System.out.println("File does not exist.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
