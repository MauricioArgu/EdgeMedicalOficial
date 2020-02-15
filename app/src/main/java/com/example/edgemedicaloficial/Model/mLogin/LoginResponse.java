package com.example.edgemedicaloficial.Model.mLogin;

import com.example.edgemedicaloficial.Model.mRegistro.Registro;

import java.util.List;

public class LoginResponse {

    int errorCode;
    String errorMessage;
    Object msg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
