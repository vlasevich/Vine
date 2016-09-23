package com.home.vlas.vine.activity.realm.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Turnover extends RealmObject {
    @PrimaryKey
    private String id;

    private String canaryId;
    private String boxCount;
    private String bottleCount;
    private String date;
    private String wineName;
    private String statusId;

    public Turnover() {
    }

    public Turnover(String bottleCount, String boxCount, String canaryId, String date, String id, String statusId, String wineName) {
        this.bottleCount = bottleCount;
        this.boxCount = boxCount;
        this.canaryId = canaryId;
        this.date = date;
        this.id = id;
        this.statusId = statusId;
        this.wineName = wineName;
    }

    public String getBottleCount() {
        return bottleCount;
    }

    public void setBottleCount(String bottleCount) {
        this.bottleCount = bottleCount;
    }

    public String getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(String boxCount) {
        this.boxCount = boxCount;
    }

    public String getCanaryId() {
        return canaryId;
    }

    public void setCanaryId(String canaryId) {
        this.canaryId = canaryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getWineName() {
        return wineName;
    }

    public void setWineName(String wineName) {
        this.wineName = wineName;
    }
}
