package com.example.absenlocation.Interface;

import android.content.Context;
import android.location.Location;

public interface LogLocationInterface {
    interface  Views{
        void setAdapter();
    }

    interface Controller{
        void getMessageWelcome(Context context);
        void updateLocation(Context context);
        void countDistance(Context context);
        void sendTrance(Context context);
    }
}
