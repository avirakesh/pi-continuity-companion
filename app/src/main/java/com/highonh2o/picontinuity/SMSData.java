package com.highonh2o.picontinuity;

/**
 * Created by avichalrakesh on 11/6/17.
 */

public class SMSData  {
    private int id;
    private int thread_id;
    private String address;
    private String date;
    private boolean sent;
    private String body;


    public SMSData(int id, int thread_id, String address, String date, boolean sent, String body) {
        this.id = id;
        this.thread_id = thread_id;
        this.address = address;
        this.date = date;
        this.sent = sent;
        this.body = body;
    }

    public int getId() {

        return id;
    }

    public int getThread_id() {
        return thread_id;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public boolean isSent() {
        return sent;
    }

    public String getBody() {
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
