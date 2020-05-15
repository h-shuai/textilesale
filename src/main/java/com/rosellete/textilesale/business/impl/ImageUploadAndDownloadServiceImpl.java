package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.ImageUploadAndDownloadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service("imageUploadAndDownloadService")
public class ImageUploadAndDownloadServiceImpl implements ImageUploadAndDownloadService {
    @Value("${imagePath}")
    private String folder;
    @Override
    public String saveImage(MultipartFile file) throws IOException {
        File folderFile=new File(folder);
        if (!folderFile.exists()){
            folderFile.mkdirs();
        }
        File localFile = new File(folder, file.getOriginalFilename());
        if (!localFile.exists()){
            localFile.createNewFile();
        }
        file.transferTo(localFile);
        return localFile.getCanonicalPath();
    }

    @Override
    public void readImage(String id, HttpServletRequest request, HttpServletResponse response) {


    }
}
