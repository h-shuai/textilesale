package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface ImageUploadAndDownloadApi {
    @PostMapping(value = "/upload")
    RestResponse uploadImage(MultipartFile file, @RequestParam Map modelMap);

    @GetMapping(path = "/download")
    void downloadImage(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException;

    @GetMapping(path = "/delete")
    RestResponse deleteImage(@RequestParam("id") String id);

}
