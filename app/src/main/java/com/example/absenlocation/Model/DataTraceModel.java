package com.example.absenlocation.Model;

public class DataTraceModel {

    private String date_time;
    private String latitude;
    private String longitude;
    private String speed;

    public DataTraceModel(String dateTime, String latitude, String longitude, String speed){
        this.date_time = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
