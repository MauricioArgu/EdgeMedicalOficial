package com.example.edgemedicaloficial.Rest;

import com.example.edgemedicaloficial.Activity.LoginActivity;
import com.example.edgemedicaloficial.Model.mAddAppoinment.AddAppointmentBody;
import com.example.edgemedicaloficial.Model.mAddAppoinment.AddAppointmentResponse;
import com.example.edgemedicaloficial.Model.mAppointment.Appointment;
import com.example.edgemedicaloficial.Model.mAppointment.AppointmentResponse;
import com.example.edgemedicaloficial.Model.mDoctor.DobByIdResponse;
import com.example.edgemedicaloficial.Model.mDoctor.DocByEspecialidadResponse;
import com.example.edgemedicaloficial.Model.mEspecialidades.EspecialidadResponse;
import com.example.edgemedicaloficial.Model.mEspecialidades.Especialidades;
import com.example.edgemedicaloficial.Model.mHoras.HorasResponse;
import com.example.edgemedicaloficial.Model.mLogin.LoginBody;
import com.example.edgemedicaloficial.Model.mLogin.LoginResponse;
import com.example.edgemedicaloficial.Model.mRecovery.Step1Body;
import com.example.edgemedicaloficial.Model.mRecovery.Step1Response;
import com.example.edgemedicaloficial.Model.mRecovery.Step2Body;
import com.example.edgemedicaloficial.Model.mRecovery.Step2Response;
import com.example.edgemedicaloficial.Model.mRecovery.Step3Body;
import com.example.edgemedicaloficial.Model.mRecovery.Step3Response;
import com.example.edgemedicaloficial.Model.mRegistro.RegistroBody;
import com.example.edgemedicaloficial.Model.mRegistro.RegistroResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface
{

    @GET("getEspecialidades")
    @Headers("Content-Type: aplication/json")
    Call<EspecialidadResponse> getEspecialidades();

    @GET("getDocByEspecialidad/{id}")
    @Headers("content-Type: application/json")
    Call<DocByEspecialidadResponse> getDocByEspecialidad(@Path("id") String id);


    @GET("getCitasAceptadas/{id}")
    @Headers("content-Type: application/json")
    Call<AppointmentResponse> getCitasAceptadas(@Path("id") String id);


    @GET("getCitasDone/{id}")
    @Headers("contect-Type: application/json")
    Call<AppointmentResponse> getCitasDone(@Path("id") String id);

    @GET("getDoctorById/{idDoctor}")
    @Headers("content-Type: application/json")
    Call<DobByIdResponse> getDoctorById (@Path("idDoctor") String idDoctor);

    @GET("getCitaInProcess/{id}")
    @Headers("content-Type: application/json")
    Call<AppointmentResponse> getCitaInProcess(@Path("id") String id);


    @GET("getHorasDisponibles/{fecha}")
    @Headers("Content-Type: application/json")
    Call<HorasResponse> getHorasDisponibles(@Path("fecha") String fecha);

    @POST("addAppointment")
    @Headers("Content-Type: application/json")
    Call<AddAppointmentResponse> addAppointment(@Body AddAppointmentBody body);


    @POST("setRegistroUsuarioApp")
    @Headers("Content-Type: application/json")
    Call<RegistroResponse> setRegistroUsuarioApp(@Body RegistroBody body);

    @POST("getLoginUsuarioApp")
    @Headers("Content-Type: application/json")
    Call<LoginResponse> getLoginUsuarioApp(@Body LoginBody body);

    @POST("passwordRecoveryStep1")
    @Headers("Content-Type: application/json")
    Call<Step1Response> passwordRecoveryStep1(@Body Step1Body body);

    @POST("passwordRecoveryStep2")
    @Headers("Content-Type: application/json")
    Call<Step2Response> passwordRecoveryStep2(@Body Step2Body body);

    @POST("passwordRecoveryStep3")
    @Headers("Content-Type: application/json")
    Call<Step3Response> passwordRecoveryStep3(@Body Step3Body body);

}
