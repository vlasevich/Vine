package com.home.vlas.vine.activity.realm.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Reminder extends RealmObject {

    @PrimaryKey
    private String id;
    private String canaryId;
    private String date;
    private String wineName;
    private String boxCount;
    private String bottleCount;
    private String text;
    private String reminderType;

    public Reminder() {
    }

    public Reminder(String bottleCount, String boxCount, String canaryId, String date, String id, String reminderType, String text, String wineName) {
        this.bottleCount = bottleCount;
        this.boxCount = boxCount;
        this.canaryId = canaryId;
        this.date = date;
        this.id = id;
        this.reminderType = reminderType;
        this.text = text;
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

    public String getReminderType() {
        return reminderType;
    }

    public void setReminderType(String reminderType) {
        this.reminderType = reminderType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWineName() {
        return wineName;
    }

    public void setWineName(String wineName) {
        this.wineName = wineName;
    }
}
