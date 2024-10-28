package com.trandokhanhminh.e_commerce.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class UploadImg {
    private String UPLOAD_FOLDER = "C:\\e-commerce\\src\\main\\resources\\static\\access\\img\\img_product";

    public boolean isUploadImage(MultipartFile multipartFile) {
        boolean isUpload = false;
        try {
            Files.copy(multipartFile.getInputStream(),
                    Paths.get(UPLOAD_FOLDER + File.separator,
                            multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return isUpload;
    }

    public String getUPLOAD_FOLDER() {
        return UPLOAD_FOLDER;
    }

    public void setUPLOAD_FOLDER(String UPLOAD_FOLDER) {
        this.UPLOAD_FOLDER = UPLOAD_FOLDER;
    }
}
