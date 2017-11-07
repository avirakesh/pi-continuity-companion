package com.highonh2o.picontinuity;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by avichalrakesh on 11/6/17.
 */

public class SmsObserverService extends Service {

    SMSObserver smsObserver = null;

    @Override
    public void onCreate() {
        Uri uri = Uri.parse("content://sms/");
        if (smsObserver == null) {
            smsObserver = new SMSObserver(new Handler(), getApplicationContext());
        }
        this.getContentResolver().registerContentObserver(uri, true, smsObserver);
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getContentResolver().unregisterContentObserver(smsObserver);
    }
}
