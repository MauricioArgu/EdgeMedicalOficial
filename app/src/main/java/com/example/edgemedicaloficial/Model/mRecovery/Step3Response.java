package com.example.edgemedicaloficial.Model.mRecovery;

public class Step3Response
{
    int errorCode;
    String errorMessage;
    Object msg;

    public Step3Response(int errorCode, String errorMessage, Object msg) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.msg = msg;
    }

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
