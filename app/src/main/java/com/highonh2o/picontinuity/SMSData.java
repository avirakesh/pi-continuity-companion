package com.highonh2o.picontinuity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by avichalrakesh on 11/6/17.
 */

public class SMSData  {

    public static Map<String, String> fieldsMap;
    static {
        fieldsMap = new HashMap<>();
        fieldsMap.put("id", "_id");
        fieldsMap.put("thread_id", "thread_id");
        fieldsMap.put("address", "phone_number");
        fieldsMap.put("date", "datetime");
        fieldsMap.put("type", "type");
        fieldsMap.put("body", "body");
    }

    private int id;
    private int thread_id;
    private String address;
    private long date;
    private boolean sent;
    private String body;


    SMSData(int id, int thread_id, String address, long date, boolean sent, String body) {
        this.id = id;
        this.thread_id = thread_id;
        this.address = address;
        this.date = date;
        this.sent = sent;
        this.body = body;
    }

    int getId() {
        return id;
    }

    int getThread_id() {
        return thread_id;
    }

    String getAddress() {
        return address;
    }

    long getDate() {
        return date;
    }

    boolean isSent() {
        return sent;
    }

    String getBody() {
        return body;
    }

    @Override
    public String toString() {

        return  "ID: " + id + "\n" +
                "Thread ID: " + thread_id + "\n" +
                "Address: " + address + "\n" +
                "Date: " + date + "\n" +
                (sent ? "Type: Sent\n" : "Type: Received\n") +
                "Body: " +  body + "\n";
    }
}
