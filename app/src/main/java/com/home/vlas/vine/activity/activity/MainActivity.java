package com.home.vlas.vine.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.home.vlas.vine.R;
import com.home.vlas.vine.activity.model.TokenRequest;
import com.home.vlas.vine.activity.model.TokenResponse;
import com.home.vlas.vine.activity.rest.ApiClient;
import com.home.vlas.vine.activity.rest.ApiInterface;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                TokenRequest tokenRequest = new TokenRequest();
                tokenRequest.setImei("54321");
                tokenRequest.setLogin("perviy");
                tokenRequest.setPassword("123456");
                retrofit2.Call<TokenResponse> tokenResponseCall = apiService.getUserToken(tokenRequest);
                tokenResponseCall.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<TokenResponse> call, Response<TokenResponse> response) {
                        System.out.println(response.code());
                        TokenResponse tokenResponse = response.body();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<TokenResponse> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });


    }
}
