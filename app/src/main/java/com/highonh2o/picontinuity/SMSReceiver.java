package com.highonh2o.picontinuity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by avichalrakesh on 11/4/17.
 */

public class SMSReceiver extends BroadcastReceiver {

    private static String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received Intent");

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            if (pdus != null) {
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte []) pdus[i]);
                    String address = messages[i].getOriginatingAddress();
                    String message = messages[i].getDisplayMessageBody();
                    Log.d(TAG, String.format("%s, %s", address, message));
                }
            }
        }
//        Intent background = new Intent(context, SMSReceiver.class);
//        context.startService(background);
    }
}
