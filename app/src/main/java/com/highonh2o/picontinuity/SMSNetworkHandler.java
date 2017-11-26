package com.highonh2o.picontinuity;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by avichalrakesh on 11/25/17.
 */

public class SMSNetworkHandler {
    private static String TAG = "SMSNetworkHandler";
    public static long lastTime = 0;
    public static int lastId = -1;

    static void queueNewMessages(final Context context, List<SMSData> newMessages) {
        Log.d(TAG, "Queuing new Messages");
        final String json = getSmsJsonString(newMessages);
        Log.d(TAG, json);

        StringRequest request = new StringRequest(Request.Method.POST, Globals.URL_BASE + Globals.ADD_SMS_EXT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject responseJson = new JSONObject(response);
                    int status = responseJson.getInt("status");
                    if (status != 0) {
                        // Ran into an issue during addition.
                        Log.d(TAG, response);
                    } else {
                        Log.d(TAG, "Network Request Successful!");
                        long latest = responseJson.getJSONObject("success_data").getLong("latest_time");
                        if (latest > Globals.getLatestSmsTime(context)) {
                            Globals.updateLatestSmsTime(context, latest);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Failed To response.\n" + error.getMessage());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return json.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(context, request);
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
