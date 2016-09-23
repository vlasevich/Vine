package com.home.vlas.vine.activity.realm.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WineInStockBottles extends RealmObject {

    @PrimaryKey
    private String total;

    private String inbox;
    private String bottle;

    public WineInStockBottles(String bottle, String inbox, String total) {
        this.bottle = bottle;
        this.inbox = inbox;
        this.total = total;
    }

    public WineInStockBottles() {
    }

    public String getBottle() {
        return bottle;
    }

    public void setBottle(String bottle) {
        this.bottle = bottle;
    }

    public String getInbox() {
        return inbox;
    }

    public void setInbox(String inbox) {
        this.inbox = inbox;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
