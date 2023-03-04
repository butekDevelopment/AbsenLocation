package com.example.absenlocation.State;

import java.util.ArrayList;

public class LogLocationState {
    static double longitude;
    static double latitude;
    static String messageWelcome;
    static ArrayList<String> listLocation = new ArrayList<>();

    public static String getMessageWelcome() {
        return messageWelcome;
    }

    public static void setMessageWelcome(String messageWelcome) {
        LogLocationState.messageWelcome = messageWelcome;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        LogLocationState.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        LogLocationState.latitude = latitude;
    }

    public static ArrayList<String> getListLocation() {
        return listLocation;
    }

    public static void setListLocation(ArrayList<String> listLocation) {
        LogLocationState.listLocation = listLocation;
    }
}
