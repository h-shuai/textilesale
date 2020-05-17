package com.rosellete.textilesale.business;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public interface ImageUploadAndDownloadService {
    String saveImage(MultipartFile file) throws IOException;

    void readImage(String id, HttpServletRequest request, HttpServletResponse response);
}