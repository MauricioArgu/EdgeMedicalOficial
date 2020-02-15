package com.example.edgemedicaloficial.Rest;

import com.example.edgemedicaloficial.Adapter.ContentAdapter;
import com.example.edgemedicaloficial.Model.mLogin.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{
    public static final String BASE_URL = "https://edgemedicalapp.com/API/V1/";

    private static Retrofit retrofit = null;
    private static Gson gson;

    public static Retrofit getApiClient()
    {
        gson = new GsonBuilder()
                .registerTypeAdapter(LoginResponse.class, new ContentAdapter())
                .create();

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
