package com.example.absenlocation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absenlocation.Controller.LogLocationController;
import com.example.absenlocation.Interface.LogLocationInterface;
import com.example.absenlocation.Model.DataTraceModel;
import com.example.absenlocation.R;
import com.example.absenlocation.State.LogLocationState;
import com.example.absenlocation.Utils.LogView;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class LogLocation extends AppCompatActivity implements View.OnClickListener, LogLocationInterface.Views {

    MaterialButton btnStart;
    LogLocationInterface.Controller controller;
    ListView listView;
    ArrayAdapter adapter;
    TextView messageWelcome, messageLog;
    LocationTrack locationTrack;
    public static ArrayList<DataTraceModel> trace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_loglocation);
        controller = new LogLocationController(this, this);
        controller.getMessageWelcome(this);
        adapter = new ArrayAdapter<>(this,
                R.layout.activity_listview, LogLocationState.getListLocation());


        listView = findViewById(R.id.mobile_list);
        btnStart = findViewById(R.id.btnStart);
        messageWelcome =  findViewById(R.id.TV_messageWelcome);
        messageLog = findViewById(R.id.messageLog);
        messageWelcome.setText(LogLocationState.getMessageWelcome());
        btnStart.setOnClickListener(this);
        /* Untuk mengambil log location secata otomatis */
//        locationTrack = new LocationTrack(this, this);
//        if (locationTrack.canGetLocation()) {
//            double longitude = locationTrack.getLongitude();
//            double latitude = locationTrack.getLatitude();
//
//            if(dummyLongitude != longitude && dummyLatitude != latitude){
//                dummyLongitude = longitude;
//                dummyLatitude = latitude;
//                listLocation.add("longitude "+ String.valueOf(longitude)+ "\nlatidute " + String.valueOf(latitude));
//                Log.d("testLocation", listLocation.toString());
//                listView.setAdapter(adapter);
//                Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
//            }
//
//        } else {
//            locationTrack.showSettingsAlert();
//        }

    }

    @SuppressLint({"NonConstantResourceId", "SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnStart) {
            locationTrack = new LocationTrack(this, this);
            if (locationTrack.canGetLocation()) {
                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();

                if (LogLocationState.getLongitude() != longitude && LogLocationState.getLatitude() != latitude) {
                    LogLocationState.setLongitude(longitude);
                    LogLocationState.setLatitude(latitude);
                    LogLocationState.getListLocation().add("longitude " + longitude + "\nlatidute " + latitude);
                    LogView logView = new LogView();
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String date = simpleDateFormat.format(new Date());
                        trace = new ArrayList<>();
                        trace.add(new DataTraceModel(date, String.valueOf(latitude), String.valueOf(longitude), String.valueOf(locationTrack.getSpeed())));
                        btnStart.setEnabled(false);
                        messageLog.setText("Log trace sedang bejalan,");

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Gagal Menyimpan Lokasi", Toast.LENGTH_SHORT).show();
                    }
                    listView.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "Longitude:" + longitude + "\nLatitude:" + latitude, Toast.LENGTH_SHORT).show();
                }

            } else {
                locationTrack.showSettingsAlert();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }

    @Override
    public void setAdapter() {
        listView.setAdapter(adapter);
    }


}