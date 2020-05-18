package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.ImageUploadAndDownloadService;
import com.rosellete.textilesale.model.SysConfig;
import com.rosellete.textilesale.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service("imageUploadAndDownloadService")
public class ImageUploadAndDownloadServiceImpl implements ImageUploadAndDownloadService {

    @Autowired
    private SysConfigService sysConfigService;


    private String getImagePath() {
        String folder;
        SysConfig imagePath = sysConfigService.findByCodeName("imagePath");
        if (null != imagePath) {
            folder = imagePath.getCodeValue();
        } else {
            folder = "/Users/shadow/upload";
        }
        return folder;
    }

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        String folder = getImagePath();
        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        String fileName = assembleFileName(file.getContentType());
        File localFile = new File(folder, fileName);
        if (!localFile.exists()) {
            localFile.createNewFile();
        }
        file.transferTo(localFile);
        return fileName;
    }

    private String assembleFileName(String contentType) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        StringBuffer result = new StringBuffer(LocalDateTime.now().format(dateTimeFormatter));
        switch (contentType) {
            case "image/jpeg":
                result.append(".jpeg");
                break;
            case "image/jpg":
                result.append(".jpg");
                break;
            case "application/x-bmp":
                result.append(".bmp");
                break;
            case "application/x-png":
            case "image/png":
                result.append(".png");
            default:
                result.append(".jpg");
                break;
        }
        return result.toString();
    }

    @Override
    public void readImage(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String folder = getImagePath();
        try (InputStream inputStream = new FileInputStream(new File(folder, id)); OutputStream outputStream = response.getOutputStream();) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + id);
            byte[] buffer = new byte[8 * 1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        }

    }

    @Override
    public void deleteImage(String id) {
        String folder = getImagePath();
        File localFile = new File(folder, id);
        if (!localFile.exists()&&localFile.isFile()) {
            localFile.delete();
        }
    }
}
