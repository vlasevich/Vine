package com.home.vlas.vine.activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class WineInStock {

    @SerializedName("wineInStock")
    @Expose
    public WineInStockBottles wineInStock;
    @SerializedName("reminder")
    @Expose
    public List<Reminder> reminder = new ArrayList<Reminder>();
    @SerializedName("turnover")
    @Expose
    public List<Turnover> turnover = new ArrayList<Turnover>();

}
