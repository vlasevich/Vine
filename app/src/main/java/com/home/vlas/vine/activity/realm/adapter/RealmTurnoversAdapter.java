package com.home.vlas.vine.activity.realm.adapter;


import android.content.Context;

import com.home.vlas.vine.activity.realm.model.Turnover;

import io.realm.RealmResults;

public class RealmTurnoversAdapter extends RealmModelAdapter<Turnover> {

    public RealmTurnoversAdapter(Context context, RealmResults<Turnover> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
