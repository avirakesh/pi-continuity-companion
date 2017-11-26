package com.highonh2o.picontinuity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by avichalrakesh on 11/7/17.
 */

class Globals {
    // Hard Coded for now.
    static final String URL_BASE = "http://192.168.2.4:8088/";
    static final String ADD_SMS_EXT = "add-sms";



    private static long latestSmsTime = 0;
    private static boolean readFromPrefs = false;

    public static void updateLatestSmsTime(Context context, long newLatest) {
        String prefsName = context.getString(R.string.package_name) + "." + context.getString(R.string.preference_file_key);
        SharedPreferences sharedPrefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);

        SharedPreferences.Editor spEdit = sharedPrefs.edit();
        spEdit.putLong(context.getString(R.string.last_timestamp), newLatest);

        spEdit.apply();
        readFromPrefs = true;
    }

    public static long getLatestSmsTime(Context context) {
        if (!readFromPrefs) {
            String prefsName = context.getString(R.string.package_name) + "." + context.getString(R.string.preference_file_key);
            SharedPreferences sharedPrefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
            latestSmsTime = sharedPrefs.getLong(context.getString(R.string.last_timestamp), 0);
            readFromPrefs = true;
        }

        return latestSmsTime;
    }

}
