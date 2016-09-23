package com.home.vlas.vine.activity.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

import com.home.vlas.vine.R;
import com.home.vlas.vine.activity.model.TokenRequest;
import com.home.vlas.vine.activity.model.TokenResponse;
import com.home.vlas.vine.activity.model.WineInStock;
import com.home.vlas.vine.activity.rest.ApiClient;
import com.home.vlas.vine.activity.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button button, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                final TokenRequest tokenRequest = new TokenRequest();
                tokenRequest.setImei("54321");
                tokenRequest.setLogin("perviy-");
                tokenRequest.setPassword("1234560");
                retrofit2.Call<TokenResponse> tokenResponseCall = apiService.getUserToken(tokenRequest);
                tokenResponseCall.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<TokenResponse> call, Response<TokenResponse> response) {
                        if (response.isSuccessful()) {
                            // Do your success stuff...
                        } else {
                            try {
                                String errorBody = response.errorBody().string();
                                String errorMsg = errorBody.substring(errorBody.indexOf("<title>"), errorBody.indexOf("</title>"));
                                System.out.println(errorMsg);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<TokenResponse> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<WineInStock> call = apiService.getWineInStock("12345", "bc9c14a1a83c9640f21cc70ab15510cf", "2");
                call.enqueue(new Callback<WineInStock>() {
                    @Override
                    public void onResponse(Call<WineInStock> call, Response<WineInStock> response) {
                        System.out.println(response.code());
                        System.out.println(response.body());
                        WineInStock wineInStock = response.body();
                        System.out.println(wineInStock.reminder.size());
                    }

                    @Override
                    public void onFailure(Call<WineInStock> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, VineStockActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    public String getIMEI(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
