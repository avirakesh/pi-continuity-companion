package com.highonh2o.picontinuity;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.Arrays;

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
        if (Globals.latestSmsSeen != null) {
            selection = String.format("%s > ? AND %s > ?", columns[3], columns[0]);
            selectArgs = new String[] {Globals.latestSmsSeen.getDate(), String.valueOf(Globals.latestSmsSeen.getId())};
        }
        Cursor c = context.getContentResolver().query(
            uri,
            columns,
            selection,
            selectArgs,
            String.format("%s DESC, %s DESC", columns[3], columns[0])
        );
//        Cursor c = getContentResolver().query(uri, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            int ctr = 0,
                target = 25;

            boolean updateLatest = true;

            do {
                StringBuilder msgData = new StringBuilder();
                for (int i = 0; i < c.getColumnCount(); i++) {
                    msgData.append(String.format("%s : %s\n", c.getColumnName(i), c.getString(i)));
                }
                Log.d(TAG, msgData.toString());
                ctr++;

                if (updateLatest) {
                    int id = c.getInt(0);
                    int thread_id = c.getInt(1);
                    String address = c.getString(2);
                    String date = c.getString(3);
                    String protocol = c.getString(4);
                    String body = c.getString(5);
                    boolean sent = false;
                    if (protocol == null) {
                        sent = true;
                    }

                    Globals.latestSmsSeen = new SMSData(id, thread_id, address, date, sent, body);

                    updateLatest = false;
                }

            } while (ctr < target && c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
    }
}
