package com.rosellete.textilesale.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rosellete.textilesale.business.ImageUploadAndDownloadService;
import com.rosellete.textilesale.interfaces.ImageUploadAndDownloadApi;
import com.rosellete.textilesale.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImageUploadAndDownloadController implements ImageUploadAndDownloadApi {
    @Autowired
    private ImageUploadAndDownloadService imageUploadAndDownloadService;

    @Override
    @PostMapping(value = "/upload")
    public RestResponse uploadImage(MultipartFile file, Map map) {
        String fileName;
        try {
            fileName=imageUploadAndDownloadService.saveImage(file);
        } catch (IOException e) {
            fileName=null;
            e.printStackTrace();
        }
        map.put("fileName",fileName);
        return new RestResponse(JSON.toJSONString(map));
    }

    @Override
    @GetMapping("download/{id}")
    public void downloadImage(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        imageUploadAndDownloadService.readImage(id,request,response);
    }

    @Override
    @GetMapping("delete")
    public RestResponse deleteImage(@RequestParam("id") String id) {
        RestResponse response= new RestResponse();

        try {
            imageUploadAndDownloadService.deleteImage(id);
        }catch (Exception e){
            response.setCode(999);
            response.setMsg("删除文件失败");
        }
        return response;

    }
}
