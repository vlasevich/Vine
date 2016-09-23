package com.home.vlas.vine.activity.realm.adapter;


import android.content.Context;

import com.home.vlas.vine.activity.realm.model.Reminder;

import io.realm.RealmResults;

public class RealmRemindersAdapter extends RealmModelAdapter<Reminder> {
    public RealmRemindersAdapter(Context context, RealmResults<Reminder> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
