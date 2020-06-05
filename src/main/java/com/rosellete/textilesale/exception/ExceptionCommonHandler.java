package com.rosellete.textilesale.exception;

import com.rosellete.textilesale.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionCommonHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestResponse<Object> handler(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException simple = (BusinessException) e;
            log.error(simple.getErrMessage(),e);
            return new RestResponse<>(simple.getCode(),simple.getErrMessage());
        }else {
            log.error("【系统异常】:[{}]", e.getMessage(), e);
            return new RestResponse<>(500,e.getMessage());
        }
    }
}
