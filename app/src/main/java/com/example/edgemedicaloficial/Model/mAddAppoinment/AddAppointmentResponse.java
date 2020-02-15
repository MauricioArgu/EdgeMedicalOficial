package com.example.edgemedicaloficial.Model.mAddAppoinment;

import java.util.List;

public class AddAppointmentResponse
{
    int errorCode;
    String errorMessage;
    List<AddAppointments> msg;

    public AddAppointmentResponse(int errorCode, String errorMessage, List<AddAppointments> msg) {
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

    public List<AddAppointments> getMsg() {
        return msg;
    }

    public void setMsg(List<AddAppointments> msg) {
        this.msg = msg;
    }
}
