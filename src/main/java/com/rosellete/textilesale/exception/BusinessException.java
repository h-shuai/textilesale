package com.rosellete.textilesale.exception;

public class BusinessException extends RuntimeException {
    private int code;
    private String errMessage;

    public BusinessException() {
        this.code = 500;
        this.errMessage = "系统异常";
    }

    public BusinessException(int code, String errMessage) {
        this.code = code;
        this.errMessage = errMessage;
    }

    public BusinessException(int code, String message, Throwable t){
        super(t);
        this.code=code;
        this.errMessage =message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
