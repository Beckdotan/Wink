package com.example.wink;

public class LinkedListNode {

    private String id;
    private long timeInMillis;

    public LinkedListNode() {
        //empty constructor.
    }

    public LinkedListNode(String id , long timeInMillis) {
        this.id = id;
        this.timeInMillis = timeInMillis;
    }


    //setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }


    //getters
    public String getId() {
        return id;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }
}
