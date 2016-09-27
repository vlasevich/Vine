package com.home.vlas.vine.activity.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.home.vlas.vine.R;
import com.home.vlas.vine.activity.adapter.RemindersAdapter;
import com.home.vlas.vine.activity.adapter.TurnoverAdapter;
import com.home.vlas.vine.activity.app.Prefs;
import com.home.vlas.vine.activity.model.TurnoverPair;
import com.home.vlas.vine.activity.model.WineInStock;
import com.home.vlas.vine.activity.realm.RealmController;
import com.home.vlas.vine.activity.realm.model.Reminder;
import com.home.vlas.vine.activity.realm.model.Turnover;
import com.home.vlas.vine.activity.realm.model.WineInStockBottles;
import com.home.vlas.vine.activity.rest.ApiClient;
import com.home.vlas.vine.activity.rest.ApiInterface;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WineStockActivity extends FragmentActivity {
    private static final String TAG = WineStockActivity.class.getSimpleName();
    private WineInStock wineInStock;
    private Realm realm;
    private String token;
    private String cellarId;
    private String imei;
    private TextView totalCount, totalBox, totalBottle;
    private RecyclerView reminderRecyclerView, turnoverRecyclerView;
    private RemindersAdapter remindersAdapter;
    private TurnoverAdapter turnoverAdapter;
    private ImageView bottle, box;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winestoke);

        ImageView dashboard = (ImageView) findViewById(R.id.dashboardIcon);
        dashboard.setBackgroundResource(R.drawable.dashboard_icon);
        ImageView wine = (ImageView) findViewById(R.id.wine);
        wine.setBackgroundResource(R.drawable.butilka_icon);
        ImageView navi = (ImageView) findViewById(R.id.navi);
        navi.setBackgroundResource(R.drawable.navigation_icon);
        ImageView alert = (ImageView) findViewById(R.id.alert);
        alert.setBackgroundResource(R.drawable.napominania_icon);
        ImageView stat = (ImageView) findViewById(R.id.stat);
        stat.setBackgroundResource(R.drawable.statistika_icon);
        ImageView news = (ImageView) findViewById(R.id.news);
        news.setBackgroundResource(R.drawable.novosti_icon);


        spinner = (ProgressBar) findViewById(R.id.progressBar);
        totalCount = (TextView) findViewById(R.id.totalCount);
        totalBox = (TextView) findViewById(R.id.inBoxCount);
        totalBottle = (TextView) findViewById(R.id.totalBottlesCount);
        bottle = (ImageView) findViewById(R.id.bottleImage);
        bottle.setBackgroundResource(R.drawable.shape);
        box = (ImageView) findViewById(R.id.boxImage);
        box.setBackgroundResource(R.drawable.boxes_pictogram);
        reminderRecyclerView = (RecyclerView) findViewById(R.id.reminderList);
        turnoverRecyclerView = (RecyclerView) findViewById(R.id.turnoverList);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        cellarId = intent.getStringExtra("cellarId");
        imei = intent.getStringExtra("imei");

        spinner.setVisibility(View.VISIBLE);

        this.realm = RealmController.with(this).getRealm();

        getData();
    }

    private void startTotal() {
        //show it first window data
        totalCount.setText(RealmController.with(this).getWineInStockBottles().first().getTotal());
        totalBox.setText(RealmController.with(this).getWineInStockBottles().first().getInbox());
        totalBottle.setText(RealmController.with(this).getWineInStockBottles().first().getBottle());
    }

    private void startReminder() {
        //show reminder data
        remindersAdapter = new RemindersAdapter(getApplicationContext(), RealmController.with(this).getREminders());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reminderRecyclerView.setLayoutManager(mLayoutManager);
        reminderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reminderRecyclerView.setAdapter(remindersAdapter);
        remindersAdapter.notifyDataSetChanged();
    }

    private void startTurnover() {
        //show turnover data
        turnoverAdapter = new TurnoverAdapter(transformTurnovers(RealmController.with(this).getTurnovers()));
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        turnoverRecyclerView.setLayoutManager(mLayoutManager2);
        turnoverRecyclerView.setItemAnimator(new DefaultItemAnimator());
        turnoverRecyclerView.setAdapter(turnoverAdapter);
        turnoverAdapter.notifyDataSetChanged();
    }

    private void getData() {
        //get data from server
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WineInStock> call = apiService.getWineInStock(imei, token, cellarId);
        call.enqueue(new Callback<WineInStock>() {
            @Override
            public void onResponse(Call<WineInStock> call, Response<WineInStock> response) {
                wineInStock = response.body();
                setData();
            }

            @Override
            public void onFailure(Call<WineInStock> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void setData() {
        //show all data
        //this.realm = RealmController.with(this).getRealm();
        //setupRecycler();
        if (!Prefs.with(this).getPreLoad()) {
            setRealmData(wineInStock);
        }
        // refresh the realm instance
        RealmController.with(this).refresh();
        startTotal();
        startReminder();
        startTurnover();
        spinner.setVisibility(View.GONE);
    }


    private void setRealmData(WineInStock wineInStock) {
        //add data to db
        ArrayList<Reminder> remindersList = new ArrayList<>();
        ArrayList<Turnover> turnoversList = new ArrayList<>();

        WineInStockBottles wineInStockBottles = new WineInStockBottles(wineInStock.wineInStock.bottle.toString(),
                wineInStock.wineInStock.inbox.toString(),
                wineInStock.wineInStock.total.toString());

        realm.beginTransaction();
        realm.copyToRealm(wineInStockBottles);
        realm.commitTransaction();

        for (com.home.vlas.vine.activity.model.Turnover t : wineInStock.turnover) {
            turnoversList.add(new Turnover(t.bottleCount.toString(),
                    t.boxCount.toString(),
                    t.canaryId.toString(),
                    t.date,
                    t.id,
                    t.statusId.toString(),
                    t.wineName));
        }

        for (Turnover t : turnoversList) {
            realm.beginTransaction();
            realm.copyToRealm(t);
            realm.commitTransaction();
        }

        for (com.home.vlas.vine.activity.model.Reminder r : wineInStock.reminder) {
            remindersList.add(new Reminder(r.bottleCount.toString(),
                    r.boxCount.toString(),
                    r.canaryId.toString(),
                    r.date,
                    r.id,
                    r.reminderType.toString(),
                    r.text,
                    r.wineName));
        }

        for (Reminder r : remindersList) {
            realm.beginTransaction();
            realm.copyToRealm(r);
            realm.commitTransaction();
        }

        Prefs.with(this).setPreLoad(true);
    }

    private List<TurnoverPair> transformTurnovers(RealmResults<Turnover> rawList) {
        // transform turnover data to pairs to show it
        List<TurnoverPair> turnoverPairs = new ArrayList<>();

        for (int i = 0; i <= rawList.size(); i++) {
            if ((i + 1) < rawList.size()) {
                if (rawList.get(i).getStatusId() != rawList.get(i + 1).getStatusId()) {
                    if (compareDates(rawList.get(i).getDate(), rawList.get(i + 1).getDate())) {
                        List<com.home.vlas.vine.activity.realm.model.Turnover> turnoverList = new ArrayList<>();
                        turnoverList.add(rawList.get(i));
                        turnoverList.add(rawList.get(i + 1));
                        turnoverPairs.add(new TurnoverPair(turnoverList));
                        i++;
                    } else {
                        List<com.home.vlas.vine.activity.realm.model.Turnover> turnoverList = new ArrayList<>();
                        turnoverList.add(rawList.get(i));
                        turnoverPairs.add(new TurnoverPair(turnoverList));
                    }
                } else {
                    List<com.home.vlas.vine.activity.realm.model.Turnover> turnoverList = new ArrayList<>();
                    turnoverList.add(rawList.get(i));
                    turnoverPairs.add(new TurnoverPair(turnoverList));
                }
            } else {
                List<com.home.vlas.vine.activity.realm.model.Turnover> turnoverList = new ArrayList<>();
                turnoverList.add(rawList.get(i - 1));
                turnoverPairs.add(new TurnoverPair(turnoverList));
            }
        }

        return turnoverPairs;
    }

    private boolean compareDates(String t1, String t2) {
        // format data and compare it
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss");
        DateTime jodaTime1 = formatter.parseDateTime(t1);
        DateTime jodaTime2 = formatter.parseDateTime(t2);

        return jodaTime1.hourOfDay().roundFloorCopy().isEqual(jodaTime2.hourOfDay().roundFloorCopy());
    }
}
