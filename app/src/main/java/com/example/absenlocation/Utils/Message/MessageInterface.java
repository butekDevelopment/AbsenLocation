package com.example.absenlocation.Utils.Message;

import android.view.View;

public interface MessageInterface {

     interface Controller{
         void SnackbarWarning(String message, View view);
         void SnackbarInfo(String message, View view);
         void SnackbarError(String message, View view);
         void SnackbarSuccesss(String message, View view);
    }
}
