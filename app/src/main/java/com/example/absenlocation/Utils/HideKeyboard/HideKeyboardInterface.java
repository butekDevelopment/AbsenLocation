package com.example.absenlocation.Utils.HideKeyboard;

import android.content.Context;
import android.os.IBinder;

public interface HideKeyboardInterface {
    interface Presenter{
        void hide(IBinder windowToken, Context context);
    }
}

