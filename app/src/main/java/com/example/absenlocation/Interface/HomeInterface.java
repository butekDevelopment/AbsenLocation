package com.example.absenlocation.Interface;

import android.content.Context;

import com.example.absenlocation.Model.Profile.ProfileModel;

import retrofit2.Response;

public interface HomeInterface {

    interface Views{
        void updateViews();
    }

    interface Controller{
        void getDataProfile();
        void logOut(Context context);
    }
}
