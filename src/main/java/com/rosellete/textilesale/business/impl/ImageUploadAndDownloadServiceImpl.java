package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.ImageUploadAndDownloadService;
import com.rosellete.textilesale.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service("imageUploadAndDownloadService")
public class ImageUploadAndDownloadServiceImpl implements ImageUploadAndDownloadService {

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public String saveImage(MultipartFile file) throws IOException {
//        String folder = sysConfigService.getImagePath();
        String folder = "/Users/Hs/Downloads/upload/";
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
    public void readImage(String id, HttpServletRequest request, HttpServletResponse response) {
        String folder = sysConfigService.getImagePath();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File localFile;
        try {
            localFile = new File(folder, id);
            if (!localFile.exists()) {
                localFile = new File(folder, "notfound.jpg");
                log.warn("图片{}未找到", id);
            }
            inputStream = new FileInputStream(localFile);
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[8 * 1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error("图片{}读取失败", id, e);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("关闭InputStream出错", e);
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("关闭OutputStream出错", e);
                }
            }

        }
        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename=" + id);
    }

    @Override
    public void deleteImage(String id) {
        String folder = sysConfigService.getImagePath();
        File localFile = new File(folder, id);
        if (localFile.exists() && localFile.isFile()) {
            localFile.delete();
        }
    }
}
