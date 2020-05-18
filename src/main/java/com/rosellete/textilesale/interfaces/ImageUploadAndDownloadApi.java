package com.rosellete.textilesale.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.rosellete.textilesale.util.RestResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface ImageUploadAndDownloadApi {
    @PostMapping(value = "/upload")
    RestResponse uploadImage(MultipartFile file,@RequestParam Map modelMap);

    @GetMapping("download/{id}")
    void downloadImage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)throws IOException;

    @GetMapping("delete/{id}")
    void deleteImage(@PathVariable String id);

}
