package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.ImageUploadAndDownloadService;
import com.rosellete.textilesale.model.SysConfig;
import com.rosellete.textilesale.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service("imageUploadAndDownloadService")
public class ImageUploadAndDownloadServiceImpl implements ImageUploadAndDownloadService {

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        String folder;
        SysConfig imagePath = sysConfigService.findByCodeName("imagePath");
        if (null!=imagePath){
            folder=imagePath.getCodeValue();
        }else {
            folder = "/Users/shadow/upload";
        }

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
