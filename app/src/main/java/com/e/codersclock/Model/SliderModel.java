package com.e.codersclock.Model;

public class SliderModel {
    int logo;
    String name;
    String event;

    public long getStart_sortingID() {
        return start_sortingID;
    }

    public void setStart_sortingID(long start_sortingID) {
        this.start_sortingID = start_sortingID;
    }

    public long getEnd_sortingID() {
        return end_sortingID;
    }

    public void setEnd_sortingID(long end_sortingID) {
        this.end_sortingID = end_sortingID;
    }

    long start_sortingID,end_sortingID;

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
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
