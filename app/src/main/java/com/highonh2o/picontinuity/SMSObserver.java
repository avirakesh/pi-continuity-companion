package com.highonh2o.picontinuity;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by avichalrakesh on 11/6/17.
 */

public class SMSObserver extends ContentObserver {

    private String TAG = this.getClass().getSimpleName();
    private static String[] columns = {
            "_id",
            "thread_id",
            "address",
            "date",
            "protocol",
            "body"
    };

    private Handler m_handler = null;
    private Context context = null;

    SMSObserver(Handler handler, Context context) {
        super(handler);
        m_handler = handler;
        this.context = context;
    }


    @Override
    public void onChange(boolean selfChange) {
//        super.onChange(selfChange);

        Uri uri = Uri.parse("content://sms");
        String selection = null;
        String[] selectArgs = null;

        String prefsName = context.getString(R.string.package_name) + "." + context.getString(R.string.preference_file_key);
        SharedPreferences sharedPrefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        int lastId = sharedPrefs.getInt(context.getString(R.string.last_id), -1);
        long lastTime = sharedPrefs.getLong(context.getString(R.string.last_timestamp), 0);
        List<SMSData> smsDataList = new ArrayList<>();


        if (Globals.latestSmsSeen != null) {
            selection = String.format("%s > ? AND %s > ?", columns[3], columns[0]);
            selectArgs = new String[]{String.valueOf(Globals.latestSmsSeen.getDate()), String.valueOf(Globals.latestSmsSeen.getId())};
        } else if (lastId != -1) {
            selection = String.format("%s > ? AND %s > ?", columns[3], columns[0]);
            selectArgs = new String[]{String.valueOf(lastTime), String.valueOf(lastId)};
        } else {
            long currTime = new Date().getTime();
            long diff = 5 * 24 * 60 * 60 * 1000;
            selection = String.format("%s >= ?", columns[3]);
            selectArgs = new String[]{String.valueOf(currTime - diff)};
        }

        Cursor c = context.getContentResolver().query(
                uri,
                columns,
                selection,
                selectArgs,
                String.format("%s DESC, %s DESC", columns[3], columns[0])
        );


        if (c != null && c.moveToFirst()) {
            int ctr = 0;

            boolean updateLatest = true;

            do {
                ctr++;
                int id = c.getInt(0);
                int thread_id = c.getInt(1);

                String address = c.getString(2);
                long date = c.getLong(3);

                String protocol = c.getString(4);
                String body = c.getString(5);

                boolean sent = false;
                if (protocol == null) {
                    sent = true;
                }

                SMSData smsData = new SMSData(id, thread_id, address, date, sent, body);

//                Log.d(TAG, smsData.toString());

                if (updateLatest) {
                    Globals.latestSmsSeen = smsData;
                    SharedPreferences.Editor spEdit = sharedPrefs.edit();
                    spEdit.putInt(context.getString(R.string.last_id), smsData.getId());
                    spEdit.putLong(context.getString(R.string.last_timestamp), smsData.getDate());
                    spEdit.apply();
                    updateLatest = false;
                }

                smsDataList.add(smsData);

            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }

        if (!smsDataList.isEmpty()) {
            SMSNetworkHandler.queueNewMessages(context, smsDataList);
        }
    }
}
