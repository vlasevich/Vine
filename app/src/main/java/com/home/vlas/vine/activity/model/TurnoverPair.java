package com.home.vlas.vine.activity.model;


import com.home.vlas.vine.activity.realm.model.Turnover;

import java.util.List;

public class TurnoverPair {
    private List<Turnover> turnoverList;

    public TurnoverPair(List<com.home.vlas.vine.activity.realm.model.Turnover> turnoverList) {
        this.turnoverList = turnoverList;
    }

    public TurnoverPair() {
    }

    public List<Turnover> getTurnoverList() {
        return turnoverList;
    }

    public void setTurnoverList(List<Turnover> turnoverList) {
        this.turnoverList = turnoverList;
    }
}
