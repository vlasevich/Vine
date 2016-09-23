package com.home.vlas.vine.activity.realm;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.home.vlas.vine.activity.realm.model.Reminder;
import com.home.vlas.vine.activity.realm.model.Turnover;
import com.home.vlas.vine.activity.realm.model.WineInStockBottles;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }


    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(Reminder.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<Reminder> getREminders() {

        return realm.where(Reminder.class).findAll();
    }

    public RealmResults<Turnover> getTurnovers() {
        return realm.where(Turnover.class).findAll();
    }

    public RealmResults<WineInStockBottles> getWineInStockBottles() {
        return realm.where(WineInStockBottles.class).findAll();
    }

    //query a single item with the given id
    public Reminder getReminder(String id) {

        return realm.where(Reminder.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasReminders() {

        return !realm.allObjects(Reminder.class).isEmpty();
    }

    //query example
    public RealmResults<Reminder> queryedReminders() {
        // TODO query example
        return realm.where(Reminder.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
