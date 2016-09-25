package com.home.vlas.vine.activity.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.home.vlas.vine.R;
import com.home.vlas.vine.activity.model.TokenRequest;
import com.home.vlas.vine.activity.model.TokenResponse;
import com.home.vlas.vine.activity.rest.ApiClient;
import com.home.vlas.vine.activity.rest.ApiInterface;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 101;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button loginButton;
    private EditText passwordEditText, loginEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.loginBtn);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginEditText = (EditText) findViewById(R.id.loginEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                final TokenRequest tokenRequest = new TokenRequest();
                tokenRequest.setImei("12345");
                Log.i(TAG, getIMEI());
                //getIMEI();
                //tokenRequest.setLogin("perviy-");
                tokenRequest.setLogin(loginEditText.getText().toString());
                //tokenRequest.setPassword("1234560");
                tokenRequest.setPassword(passwordEditText.getText().toString());
                retrofit2.Call<TokenResponse> tokenResponseCall = apiService.getUserToken(tokenRequest);
                tokenResponseCall.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<TokenResponse> call, Response<TokenResponse> response) {
                        if (response.isSuccessful()) {
                            TokenResponse tokenResponse = response.body();
                            startWineStockActivity(tokenResponse.getAccessToken(), tokenResponse.getCellarId());
                        } else {
                            try {
                                String error = getErrorMessage(response.errorBody().string());
                                passwordEditText.setError(error);

                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<TokenResponse> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
            }
        });
    }

    private void startWineStockActivity(String token, String cellarId) {
        Intent wineStockIntent = new Intent(MainActivity.this, VineStockActivity.class);
        wineStockIntent.putExtra("token", token);
        wineStockIntent.putExtra("cellarId", cellarId);
        wineStockIntent.putExtra("imei", "12345");
        MainActivity.this.startActivity(wineStockIntent);
    }

    private String getErrorMessage(String errorBody) {
        int errorMsgCode = Integer.parseInt(errorBody.substring(errorBody.indexOf("<title>"), errorBody.indexOf("</title>")).replaceAll("[^0-9]", ""));
        switch (errorMsgCode) {
            case 400: {
                return "Enter login and password";
            }
            case 404: {
                return "No route found";
            }
            case 500: {
                return "Notice: Undefined property: stdClass::$password";
            }
            default: {
                return "Unknown error";
            }
        }
    }

    public String getIMEI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                return getDeviceImei();
            }
        }

        return "";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceImei();
        }
    }

    private String getDeviceImei() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        return mTelephonyManager.getDeviceId();
    }
}
