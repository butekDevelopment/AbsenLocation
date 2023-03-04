package com.example.absenlocation.Activity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.absenlocation.Controller.LoginController;
import com.example.absenlocation.Interface.LoginInterface;
import com.example.absenlocation.State.LoginState;
import com.example.absenlocation.R;
import com.example.absenlocation.Utils.HideKeyboard.HideKeyboardController;
import com.example.absenlocation.Utils.HideKeyboard.HideKeyboardInterface;
import com.example.absenlocation.Utils.LogView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class Login extends AppCompatActivity implements LoginInterface.Views, View.OnClickListener {

    public TextInputEditText Email, Password;
    TextInputLayout TI_Nis, TI_password;
    MaterialButton btnMasuk, btnDaftar;
    LinearLayout constraintLayout;
    HideKeyboardInterface.Presenter controllerKeyboard;
    public View contentView;
    LoginInterface.Controller controller;
    ProgressBar loading;

    private final static int ALL_PERMISSIONS_RESULT = 101;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_login);

        contentView = findViewById(android.R.id.content);
        controller = new LoginController(this, this);
        controllerKeyboard = new HideKeyboardController(this);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        controller.userIsLogin(this);

        constraintLayout = findViewById(R.id.parentLogin);
        TI_Nis = findViewById(R.id.TI_email);
        Email = findViewById(R.id.TE_email);
        TI_password = findViewById(R.id.TI_password);
        Password = findViewById(R.id.TE_password);
        btnMasuk = findViewById(R.id.btnMasuk);
        btnDaftar = findViewById(R.id.btnDaftar);
        loading = findViewById(R.id.PB_login);


        constraintLayout.setOnClickListener(this);
        btnMasuk.setOnClickListener(this);
        btnDaftar.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        LogView logView = new LogView();
        switch (view.getId()){
            case R.id.btnMasuk:
                LoginState.setEmail(String.valueOf(Email.getText()));
                LoginState.setPassword(String.valueOf(Password.getText()));
                controllerKeyboard.hide(getWindow().getDecorView().getRootView().getWindowToken(), Login.this);
                boolean isValid = controller.checkInputan(this, contentView);
                if (isValid){
                    controllerKeyboard.hide(getWindow().getDecorView().getRootView().getWindowToken(), Login.this);
                    controller.doLogin(this, contentView, btnMasuk, loading);
                }
                break;
            case R.id.btnDaftar:
                controllerKeyboard.hide(getWindow().getDecorView().getRootView().getWindowToken(), Login.this);
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
                break;
            case R.id.parentLogin:
                controllerKeyboard.hide(getWindow().getDecorView().getRootView().getWindowToken(), Login.this);
                break;
        }
    }

    @Override
    public void errorInput() {
        if(LoginState.getEmail().isEmpty()){
            TI_Nis.setError("NIS tidak boleh kosong");
        }else{
            TI_Nis.setError(null);
            TI_Nis.setErrorEnabled(false);
        }

        if(LoginState.getPassword().isEmpty()){
            TI_password.setError("Password tidak boleh kosong");
        }else{
            TI_password.setError(null);
            TI_password.setErrorEnabled(false);
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel(
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage("These permissions are mandatory for the application. Please allow access.")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}