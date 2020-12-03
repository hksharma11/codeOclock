package com.e.codersclock.Model;

public class Contest {

    String name;
    String event;
    String time,date;


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getEnd_sortingID() {
        return end_sortingID;
    }

    public void setEnd_sortingID(String end_sortingID) {
        this.end_sortingID = end_sortingID;
    }

    public String getStart_sortingID() {
        return start_sortingID;
    }

    public void setStart_sortingID(String start_sortingID) {
        this.start_sortingID = start_sortingID;
    }

    String href,end_sortingID,start_sortingID;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }






}
