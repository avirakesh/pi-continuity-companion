package com.highonh2o.picontinuity;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by avichalrakesh on 11/6/17.
 */

public class SMSObserver extends ContentObserver {

    private Handler m_handler = null;
    private String TAG = this.getClass().getSimpleName();
    private String[] columns = {
        "_id",
        "thread_id",
        "address",
        "protocol",
        "body"
    };
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
        Cursor c = context.getContentResolver().query(uri, columns, null, null, null);
//        Cursor c = getContentResolver().query(uri, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            int ctr = 0,
                target = 50;

            do {
                StringBuilder msgData = new StringBuilder();
                for (int i = 0; i < c.getColumnCount(); i++) {
                    msgData.append(String.format("%s : %s\n", c.getColumnName(i), c.getString(i)));
                }
                Log.d(TAG, msgData.toString());
                ctr++;
            } while (ctr < target && c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
    }
}
