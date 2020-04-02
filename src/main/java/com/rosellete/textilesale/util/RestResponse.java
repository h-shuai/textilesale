package com.rosellete.textilesale.util;

import lombok.Data;

@Data
public class RestResponse<T> {
    private T data;
    private int code;
    private String msg;

    public RestResponse() {
        this.msg = "SUCCESS";
        this.code = 200;
    }


    public RestResponse(T data) {
        this.data = data;
        this.msg = "SUCCESS";
        this.code = 200;
    }

    public RestResponse(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public RestResponse(int code, String msg, T data) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}
