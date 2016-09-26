package com.home.vlas.vine.activity.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class VineStockActivity extends FragmentActivity {
    private WineInStock wineInStock;
    private Realm realm;
    private String token;
    private String cellarId;
    private String imei;
    private TextView totalCount;
    private RecyclerView reminderRecyclerView, turnoverRecyclerView;
    private RemindersAdapter remindersAdapter;
    private TurnoverAdapter turnoverAdapter;
    private List<TurnoverPair> turnoverPair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winestoke);

        //Button button2 = (Button) findViewById(R.id.button2);
        TextView totalCount = (TextView) findViewById(R.id.totalCount);

        totalCount.setText(RealmController.with(this).getWineInStockBottles().first().getTotal());


        reminderRecyclerView = (RecyclerView) findViewById(R.id.reminderList);

        turnoverRecyclerView = (RecyclerView) findViewById(R.id.turnoverList);

        turnoverAdapter = new TurnoverAdapter(transformTurnovers(RealmController.with(this).getTurnovers()));
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        turnoverRecyclerView.setLayoutManager(mLayoutManager2);
        turnoverRecyclerView.setItemAnimator(new DefaultItemAnimator());
        turnoverRecyclerView.setAdapter(turnoverAdapter);
        turnoverAdapter.notifyDataSetChanged();

        remindersAdapter = new RemindersAdapter(getApplicationContext(), RealmController.with(this).getREminders());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reminderRecyclerView.setLayoutManager(mLayoutManager);
        reminderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reminderRecyclerView.setAdapter(remindersAdapter);
        remindersAdapter.notifyDataSetChanged();

        /*button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click");
            }
        });*/

        turnoverPair = transformTurnovers(RealmController.with(this).getTurnovers());
        System.out.println("============================");
        System.out.println(turnoverPair.get(17).getTurnoverList().get(0).getWineName());
        System.out.println(turnoverPair.get(17).getTurnoverList().get(1).getBoxCount());
        System.out.println("============================");

        this.realm = RealmController.with(this).getRealm();
        /*for (Reminder reminder: RealmController.with(this).getREminders()){
            Log.i("Reminder",reminder.getBottleCount()+" | "+reminder.getWineName());
        }*/
        //System.out.println(RealmController.with(this).getTurnovers().size());

        /*for (Turnover turnover : RealmController.with(this).getTurnovers()) {
            Log.i("Reminder",turnover.getBottleCount()+" | "+turnover.getWineName());
        }*/

/*        WineInStockBottles w = new WineInStockBottles();
        if (!RealmController.with(this).getWineInStockBottles().isEmpty()) {
            w = RealmController.with(this).getWineInStockBottles().first();
            System.out.println("Bottles: " + w.getBottle());
            System.out.println("Inbox: " + w.getInbox());
            System.out.println("Total: " + w.getTotal());
        }*/

        Intent intent = getIntent();
        //String value = intent.getStringExtra("key"); //if it's a string you stored.
        token = intent.getStringExtra("token");
        cellarId = intent.getStringExtra("cellarId");
        imei = intent.getStringExtra("imei");

        getData();
    }

    private void getData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WineInStock> call = apiService.getWineInStock(imei, token, cellarId);
        call.enqueue(new Callback<WineInStock>() {
            @Override
            public void onResponse(Call<WineInStock> call, Response<WineInStock> response) {
                //System.out.println(response.code());
                //System.out.println(response.body());
                wineInStock = response.body();
                //System.out.println(wineInStock.reminder.size());
                setData();
            }

            @Override
            public void onFailure(Call<WineInStock> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void setData() {
        //this.realm = RealmController.with(this).getRealm();
        //setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData(wineInStock);
        }

        // refresh the realm instance
        RealmController.with(this).refresh();

/*      for (Reminder reminder: RealmController.with(this).getREminders()){
          Log.i("Reminder",reminder.getBottleCount()+" | "+reminder.getWineName());
      }*/
    }


    private void setRealmData(WineInStock wineInStock) {

        ArrayList<Reminder> remindersList = new ArrayList<>();
        ArrayList<Turnover> turnoversList = new ArrayList<>();
        ArrayList<WineInStockBottles> wineInStockBottlesList = new ArrayList<>();

        /*Book book = new Book();
        book.setId(q + System.currentTimeMillis());
        book.setAuthor("Reto Meier");
        book.setTitle("Android 4 Application Development");
        book.setImageUrl("http://api.androidhive.info/images/realm/1.png");
        books.add(book);*/
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
                    t.date.toString(),
                    t.id.toString(),
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
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(r);
            realm.commitTransaction();
        }

        Prefs.with(this).setPreLoad(true);

        for (Reminder reminder : remindersList) {
            Log.i("Reminder", reminder.getBottleCount() + " | " + reminder.getWineName());
        }

    }

    private List<TurnoverPair> transformTurnovers(RealmResults<Turnover> rawList) {
        //TurnoverPair turnoverPair=new TurnoverPair();
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
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss");
        DateTime jodaTime1 = formatter.parseDateTime(t1);
        DateTime jodaTime2 = formatter.parseDateTime(t2);

        return jodaTime1.hourOfDay().roundFloorCopy().isEqual(jodaTime2.hourOfDay().roundFloorCopy());
    }
}
