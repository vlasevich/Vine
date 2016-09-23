package com.home.vlas.vine.activity.realm;

@io.realm.annotations.RealmModule(classes = {
        com.home.vlas.vine.activity.realm.model.Reminder.class,
        com.home.vlas.vine.activity.realm.model.Turnover.class,
        com.home.vlas.vine.activity.realm.model.WineInStockBottles.class})
public class RealmModule {

}
