package com.example.paypalspringbootdemo.base;

/**
 * @author hsl
 */
public class BaseResponse {
    private String error_code = "0";
    private String error_msg = "success";

    public BaseResponse() {
    }

    public BaseResponse(String error_code, String error_msg) {
        this.error_code = error_code;
        this.error_msg = error_msg;
    }

    public String getError_code() {
        return this.error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return this.error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}