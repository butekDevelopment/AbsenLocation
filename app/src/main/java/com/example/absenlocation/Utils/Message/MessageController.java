package com.example.absenlocation.Utils.Message;

import android.content.Context;
import android.os.Build;
import android.view.View;

import com.example.absenlocation.R;
import com.google.android.material.snackbar.Snackbar;

public class MessageController implements MessageInterface.Controller {

    private final Context context;

    public MessageController(Context context) {
        this.context = context;
    }

    @Override
    public void SnackbarWarning(String message, View view) {
        Snackbar warning = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            warning.setTextColor(context.getResources().getColor(R.color.messageBlack, context.getTheme()));
            warning.getView().setBackgroundColor(context.getResources().getColor(R.color.warning, context.getTheme()));
        } else {
            warning.setTextColor(context.getResources().getColor(R.color.messageBlack));
            warning.getView().setBackgroundColor(context.getResources().getColor(R.color.warning));
        }

        warning.show();
    }

    @Override
    public void SnackbarInfo(String message, View view) {
        Snackbar warning = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            warning.setTextColor(context.getResources().getColor(R.color.messageWhite, context.getTheme()));
            warning.getView().setBackgroundColor(context.getResources().getColor(R.color.info, context.getTheme()));
        } else {
            warning.setTextColor(context.getResources().getColor(R.color.messageWhite));
            warning.getView().setBackgroundColor(context.getResources().getColor(R.color.info));
        }

        warning.show();
    }

    @Override
    public void SnackbarError(String message, View view) {
        Snackbar warning = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            warning.setTextColor(context.getResources().getColor(R.color.messageWhite, context.getTheme()));
            warning.getView().setBackgroundColor(context.getResources().getColor(R.color.error, context.getTheme()));
        } else {
            warning.setTextColor(context.getResources().getColor(R.color.messageWhite));
            warning.getView().setBackgroundColor(context.getResources().getColor(R.color.error));
        }

        warning.show();
    }

    @Override
    public void SnackbarSuccesss(String message, View view) {
        Snackbar warning = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            warning.setTextColor(context.getResources().getColor(R.color.messageWhite, context.getTheme()));
            warning.getView().setBackgroundColor(context.getResources().getColor(R.color.success, context.getTheme()));
        } else {
            warning.setTextColor(context.getResources().getColor(R.color.messageWhite));
            warning.getView().setBackgroundColor(context.getResources().getColor(R.color.success));
        }

        warning.show();
    }

}
