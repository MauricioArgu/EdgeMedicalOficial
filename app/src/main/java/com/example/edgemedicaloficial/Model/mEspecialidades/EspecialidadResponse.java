package com.example.edgemedicaloficial.Model.mEspecialidades;

import java.util.List;

public class EspecialidadResponse
{
    int errorCode;
    String errorMessage;
    List<Especialidades> msg;

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

    public List<Especialidades> getMsg() {
        return msg;
    }

    public void setMsg(List<Especialidades> msg) {
        this.msg = msg;
    }
}
