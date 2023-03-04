package com.example.absenlocation.Interface;

import android.content.Context;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;

public interface LoginInterface {
    interface Views {
        void errorInput();
    }

    interface Controller {
        void userIsLogin(Context context);
        boolean checkInputan(Context context, android.view.View contextView);
        void doLogin(Context context, android.view.View contextView, MaterialButton btnLogin, ProgressBar loading);
        void removeSharedPrefences(Context context);
    }
}

