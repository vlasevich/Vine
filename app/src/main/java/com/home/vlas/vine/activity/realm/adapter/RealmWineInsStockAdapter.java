package com.home.vlas.vine.activity.realm.adapter;


import android.content.Context;

import com.home.vlas.vine.activity.realm.model.WineInStockBottles;

import io.realm.RealmResults;

public class RealmWineInsStockAdapter extends RealmModelAdapter<WineInStockBottles> {
    public RealmWineInsStockAdapter(Context context, RealmResults<WineInStockBottles> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
