package com.home.vlas.vine.activity.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.home.vlas.vine.R;
import com.home.vlas.vine.activity.model.WineInStock;
import com.home.vlas.vine.activity.rest.ApiClient;
import com.home.vlas.vine.activity.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VineStockActivity extends Activity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winestoke);

        Intent intent = getIntent();
        //String value = intent.getStringExtra("key"); //if it's a string you stored.

        button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
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
    }
}
