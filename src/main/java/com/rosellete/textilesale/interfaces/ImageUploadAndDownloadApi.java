package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ImageUploadAndDownloadApi {
    @PostMapping(value = "/upload")
    RestResponse uploadImage(MultipartFile file);

    @GetMapping("download/{id}")
    RestResponse downloadImage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response);
}
