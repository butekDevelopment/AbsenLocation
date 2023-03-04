package com.example.absenlocation.Utils.HideKeyboard;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public class HideKeyboardController implements HideKeyboardInterface.Presenter {

    Context context;

    public HideKeyboardController(Context context) {
        this.context = context;
    }

    @Override
    public void hide(IBinder windowToken, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }
}
