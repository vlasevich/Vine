package com.home.vlas.vine.activity.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Reminder {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("canary_id")
    @Expose
    public Integer canaryId;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("wineName")
    @Expose
    public String wineName;
    @SerializedName("box_count")
    @Expose
    public Integer boxCount;
    @SerializedName("bottle_count")
    @Expose
    public Integer bottleCount;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("ReminderType")
    @Expose
    public Integer reminderType;

}

