package com.example.absenlocation.Controller;

import android.content.Context;
import android.view.View;

import com.example.absenlocation.Activity.Register;
import com.example.absenlocation.Interface.RegisterInterface;
import com.example.absenlocation.State.LoginState;
import com.example.absenlocation.State.RegisterState;

public class RegisterController implements RegisterInterface.Controller {

    Context context;
    RegisterInterface.Views views;

    public RegisterController(Register register, RegisterInterface.Views views){
        this.context = register;
        this.views = views;
    }

    @Override
    public boolean checkInputan(Context context, View contextView) {
        if(RegisterState.getNis().isEmpty() || RegisterState.getPassword().isEmpty()){
            views.errorInput();
            return false;
        }else{
            if(RegisterState.getPassword().equals(RegisterState.getRepeatPassword())){
                views.errorInput();
                return true;
            }else{
                views.errorInput();
                return false;
            }

        }
    }
}
