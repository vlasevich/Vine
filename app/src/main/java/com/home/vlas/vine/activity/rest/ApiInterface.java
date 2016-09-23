package com.home.vlas.vine.activity.rest;


import com.home.vlas.vine.activity.model.TokenRequest;
import com.home.vlas.vine.activity.model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/api/v1/auth")
    Call<TokenResponse> getUserToken(@Body TokenRequest tokenRequest);
    /*
{
        "imei" : "54321",
        "login" : "perviy",
        "password" : "123456"
        }*/
}


