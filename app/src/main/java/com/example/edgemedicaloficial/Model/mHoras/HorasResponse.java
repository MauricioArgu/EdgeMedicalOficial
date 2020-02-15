package com.example.edgemedicaloficial.Model.mHoras;

import com.example.edgemedicaloficial.Model.mAppointment.Appointment;

import java.util.List;

public class HorasResponse
{
    int errorCode;
    String errorMessage;
    List<Horas> msg;

    public HorasResponse(int errorCode, String errorMessage, List<Horas> msg) {
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

    public List<Horas> getMsg() {
        return msg;
    }

    public void setMsg(List<Horas> msg) {
        this.msg = msg;
    }
}
