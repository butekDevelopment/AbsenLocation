package com.example.absenlocation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absenlocation.Controller.HomeController;
import com.example.absenlocation.Interface.HomeInterface;
import com.example.absenlocation.Model.Profile.ProfileModel;
import com.example.absenlocation.R;
import com.example.absenlocation.State.HomeState;
import com.example.absenlocation.Utils.LogView;
import com.example.absenlocation.Utils.Message.MessageController;
import com.example.absenlocation.Utils.Message.MessageInterface;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import retrofit2.Response;

public class Home extends AppCompatActivity implements View.OnClickListener, HomeInterface.Views {

    TextView indukSiswa, namaSiswa, emailSiswa;
    MaterialButton btnAbsen, btnLogOut;
    HomeInterface.Controller controller;
    public View vView;
    MessageInterface.Controller messagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        setContentView(R.layout.activity_home);
        controller = new HomeController(this, this);
        controller.getDataProfile();

        vView = findViewById(android.R.id.content);
        indukSiswa = findViewById(R.id.TV_indukSiswa);
        namaSiswa = findViewById(R.id.TV_nameSiswa);
        emailSiswa = findViewById(R.id.TV_emailSiswa);
        btnAbsen = findViewById(R.id.btnAbsen);
        btnLogOut = findViewById(R.id.btnLogOut);

        btnAbsen.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnAbsen:
                if (HomeState.isIsDoneTracing()) {
                    messagePresenter = new MessageController(this);
                    messagePresenter.SnackbarInfo("Anda sudah melakukan tracing", vView);
                } else {
                    Intent intent = new Intent(this, LogLocation.class);
                    this.startActivity(intent);
                }
                break;
            case R.id.btnLogOut:
                controller.logOut(this);
                break;
        }
    }

    @Override
    public void updateViews() {
        indukSiswa.setText(HomeState.getIndukSiswa());
        namaSiswa.setText(HomeState.getNamaSiswa());
        emailSiswa.setText(HomeState.getEmailSiswa());
    }
}