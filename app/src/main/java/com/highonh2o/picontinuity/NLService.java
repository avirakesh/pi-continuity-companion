package com.highonh2o.picontinuity;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by avichalrakesh on 11/4/17.
 */

public class NLService extends NotificationListenerService {
    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(TAG, "Notification Posted");
        super.onNotificationPosted(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG, "Notification Removed");
        super.onNotificationRemoved(sbn);
    }
}
