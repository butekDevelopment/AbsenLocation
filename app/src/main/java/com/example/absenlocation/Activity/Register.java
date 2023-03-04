package com.example.absenlocation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.absenlocation.Controller.RegisterController;
import com.example.absenlocation.Interface.RegisterInterface;
import com.example.absenlocation.R;
import com.example.absenlocation.State.RegisterState;
import com.example.absenlocation.Utils.HideKeyboard.HideKeyboardController;
import com.example.absenlocation.Utils.HideKeyboard.HideKeyboardInterface;
import com.example.absenlocation.Utils.LogView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Register extends AppCompatActivity implements RegisterInterface.Views, View.OnClickListener {

    RegisterInterface.Controller controller;
    public View contentView;
    MaterialButton btnRegister, btnBackLogin;
    TextInputLayout TI_nis, TI_password, TI_repeatPassword;
    TextInputEditText nis, password, repeatPassword;
    LinearLayout parentView;
    HideKeyboardInterface.Presenter controllerKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_register);
        contentView = findViewById(android.R.id.content);
        controller = new RegisterController(this, this);
        controllerKeyboard = new HideKeyboardController(this);

        parentView = findViewById(R.id.parentRegister);
        TI_nis = findViewById(R.id.TI_nis);
        TI_password = findViewById(R.id.TI_password);
        TI_repeatPassword = findViewById(R.id.TI_repeatPassword);
        nis = findViewById(R.id.TE_nis);
        password = findViewById(R.id.TE_password);
        repeatPassword = findViewById(R.id.TE_repeatPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnBackLogin = findViewById(R.id.btnToLogin);

        btnRegister.setOnClickListener(this);
        btnBackLogin.setOnClickListener(this);
        parentView.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        LogView log = new LogView();
        switch (view.getId()) {
            case R.id.btnRegister:
                controllerKeyboard.hide(getWindow().getDecorView().getRootView().getWindowToken(), Register.this);
                RegisterState.setNis(String.valueOf(nis.getText()));
                RegisterState.setPassword(String.valueOf(password.getText()));
                RegisterState.setRepeatPassword(String.valueOf(repeatPassword.getText()));
                boolean isValid = controller.checkInputan(this, contentView);
                if (isValid) {
                    LogView a = new LogView();
                    a.showLog("Register Valid");
                }
                break;
            case R.id.btnToLogin:
                controllerKeyboard.hide(getWindow().getDecorView().getRootView().getWindowToken(), Register.this);
                Intent intent = new Intent(this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.parentRegister:
                log.showLog("test");
                controllerKeyboard.hide(getWindow().getDecorView().getRootView().getWindowToken(), Register.this);
                break;
        }
    }

    @Override
    public void errorInput() {
        if (RegisterState.getNis().isEmpty()) {
            TI_nis.setError("NIS tidak boleh kosong");
        } else {
            TI_nis.setError(null);
            TI_nis.setErrorEnabled(false);
        }

        if (RegisterState.getPassword().isEmpty()) {
            TI_password.setError("Password tidak boleh kosong");
        } else {
            TI_password.setError(null);
            TI_password.setErrorEnabled(false);
            if (!RegisterState.getPassword().equals(RegisterState.getRepeatPassword())) {
                TI_repeatPassword.setError("Password tidak sama");
            } else {
                TI_repeatPassword.setError(null);
                TI_repeatPassword.setErrorEnabled(false);
            }

        }
    }


}