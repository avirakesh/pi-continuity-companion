package com.highonh2o.picontinuity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    private String[] columns = {
        "_id",
        "thread_id",
        "address",
        "protocol",
        "body"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Uri uri = Uri.parse("content://sms");
        Cursor c = getContentResolver().query(uri, columns, null, null, null);
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
        }*/

        Intent intent = new Intent(this, SmsObserverService.class);
        startService(intent);
    }
}
