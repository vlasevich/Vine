package com.home.vlas.vine.activity.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class WineInStockBottles {

    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("inbox")
    @Expose
    public Integer inbox;
    @SerializedName("bottle")
    @Expose
    public Integer bottle;

}
