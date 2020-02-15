package com.example.edgemedicaloficial.Model.mDoctor;

import com.example.edgemedicaloficial.Model.mDoctor.Doctor;

import java.util.List;

public class DocByEspecialidadResponse
{
    int errorCode;
    String errorMessage;
    List<Doctor> msg;

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

    public List<Doctor> getMsg() {
        return msg;
    }

    public void setMsg(List<Doctor> msg) {
        this.msg = msg;
    }
}
