package com.example.absenlocation.Interface;

import android.content.Context;

public interface RegisterInterface {
    interface Views{
        void errorInput();
    }

    interface Controller{
        boolean checkInputan(Context context, android.view.View contextView);
    }
}
