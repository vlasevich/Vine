package com.home.vlas.vine.activity.model;


import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class Imei {
    private String imei = null;

    public Imei(Context context) {
        //String identifier = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null)
            imei = tm.getDeviceId();
        if (imei == null || imei.length() == 0)
            imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getImei() {
        return imei;
    }
}
