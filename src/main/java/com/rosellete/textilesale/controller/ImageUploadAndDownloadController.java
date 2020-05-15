package com.rosellete.textilesale.controller;

import com.rosellete.textilesale.business.ImageUploadAndDownloadService;
import com.rosellete.textilesale.interfaces.ImageUploadAndDownloadApi;
import com.rosellete.textilesale.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageUploadAndDownloadController implements ImageUploadAndDownloadApi {
    @Autowired
    private ImageUploadAndDownloadService imageUploadAndDownloadService;

    @Override
    @PostMapping(value = "/upload")
    public RestResponse uploadImage(MultipartFile file) {
        try {
            imageUploadAndDownloadService.saveImage(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @GetMapping("download/{id}")
    public RestResponse downloadImage(String id, HttpServletRequest request, HttpServletResponse response) {
        imageUploadAndDownloadService.readImage(id,request,response);
        return null;
    }
}
