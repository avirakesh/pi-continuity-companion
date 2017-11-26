package com.highonh2o.picontinuity;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by avichalrakesh on 11/25/17.
 */

public class SMSNetworkHandler {
    private static String TAG = "SMSNetworkHandler";

    static void queueNewMessages(Context context, List<SMSData> newMessages) {
        Log.d(TAG, "Queuing new Messages");
        String json = getSmsJsonString(newMessages);
        Log.d(TAG, json);
    }

    private static String getSmsJsonString(List<SMSData> newMessages) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", newMessages.size());

            JSONArray itemArray = new JSONArray();

            for (SMSData sms : newMessages) {
                JSONObject smsObject = new JSONObject();

                smsObject.put(SMSData.fieldsMap.get("id"), sms.getId());
                smsObject.put(SMSData.fieldsMap.get("thread_id"), sms.getThread_id());
                smsObject.put(SMSData.fieldsMap.get("address"), sms.getAddress());
                smsObject.put(SMSData.fieldsMap.get("date"), sms.getDate());
                smsObject.put(SMSData.fieldsMap.get("type"), sms.isSent() ? 1 : -1);
                smsObject.put(SMSData.fieldsMap.get("body"), sms.getBody());

                itemArray.put(smsObject);
            }

            jsonObject.put("items", itemArray);

            return jsonObject.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "{\"total\": 0, \"items\": []}";
    }

}
