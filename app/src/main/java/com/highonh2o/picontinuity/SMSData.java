package com.highonh2o.picontinuity;

/**
 * Created by avichalrakesh on 11/6/17.
 */

public class SMSData  {
    private int id;
    private int thread_id;
    private String address;
    private boolean sent;
    private String body;


    public SMSData(int id, int thread_id, String address, boolean sent, String body) {
        this.id = id;
        this.thread_id = thread_id;
        this.address = address;
        this.sent = sent;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("ID: ");
        builder.append(id);
        builder.append("\n");

        builder.append("Thread ID: ");
        builder.append(thread_id);
        builder.append("\n");

        builder.append("Address: ");
        builder.append(address);

        builder.append(sent ? "Type: Sent\n" : "Type: Received\n");

        builder.append("Body: ");
        builder.append(body);
        builder.append("\n");

        return builder.toString();
    }
}
