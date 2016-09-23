package com.home.vlas.vine.activity.rest;


import com.home.vlas.vine.activity.model.TokenRequest;
import com.home.vlas.vine.activity.model.TokenResponse;
import com.home.vlas.vine.activity.model.WineInStock;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("/api/v1/auth")
    Call<TokenResponse> getUserToken(@Body TokenRequest tokenRequest);

    @GET("/api/v1/dashboard")
    Call<WineInStock> getWineInStock(@Query("imei") String imei, @Query("access_token") String accessToken, @Query("cellar_id") String cellarId);
    /*
{
        "imei" : "54321",
        "login" : "perviy",
        "password" : "123456"
        }*/
}


