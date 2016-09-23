package com.home.vlas.vine.activity.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName(("access_token"))
    private String token;
    @SerializedName("cellar_id")
    private int cellarId;

    public User(int cellarId, String token) {
        this.cellarId = cellarId;
        this.token = token;
    }

    public int getCellarId() {
        return cellarId;
    }

    public void setCellarId(int cellarId) {
        this.cellarId = cellarId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
