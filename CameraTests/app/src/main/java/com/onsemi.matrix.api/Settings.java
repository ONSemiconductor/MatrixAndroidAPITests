package com.onsemi.matrix.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.onsemiconductor.cameratests.R;

public class Settings {
    private static final int defaultTimeout = 5000;

    private static final String defaultIP = "192.168.1.168";

    private static Context context = null;

    public static void setContext(Context c) {
        context = c;
    }

    public static Context getContext() {
        return context;
    }

    public static int getDefaultTimeout() {
        return defaultTimeout;
    }

    public static String getHostname(){
        return String.format("http://%s", getIP());
    }

    public static String getIP() {
        if (context == null) {
            throw new IllegalStateException("context is null");
        }

        String ip = context.getSharedPreferences(context.getString(R.string.settings_file_key),
                Context.MODE_PRIVATE).getString(context.getString(R.string.saved_ip), null);

        if (ip == null || ip == "") {
            return defaultIP;
        }

        return ip;
    }

    public static void setIP(String ip) {
        if (context == null) {
            throw new IllegalStateException("context is null");
        }

        SharedPreferences.Editor editor = context.getSharedPreferences(
                context.getString(R.string.settings_file_key), Context.MODE_PRIVATE).edit();

        editor.putString(context.getString(R.string.saved_ip), ip);

        editor.commit();
    }
}
