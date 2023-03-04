package com.example.absenlocation.Controller;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.absenlocation.Activity.Home;
import com.example.absenlocation.Activity.LogLocation;
import com.example.absenlocation.Activity.LocationTrack;
import com.example.absenlocation.Activity.Login;
import com.example.absenlocation.Interface.LogLocationInterface;
import com.example.absenlocation.Model.DataTraceModel;
import com.example.absenlocation.Model.InsertTraceModel;
import com.example.absenlocation.Rest.ApiClient;
import com.example.absenlocation.State.HomeState;
import com.example.absenlocation.State.LogLocationState;
import com.example.absenlocation.Utils.LogView;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogLocationController implements LogLocationInterface.Controller {

    LogLocation context;
    LocationTrack locationTrack;
    LogLocationInterface.Views views;
    SharedPreferences preferences;

    public LogLocationController(LogLocation home, LogLocationInterface.Views views) {
        this.context = home;
        this.views = views;
    }

    @Override
    public void getMessageWelcome(Context context) {
        preferences = context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        LogLocationState.setMessageWelcome(preferences.getString("messageWelcome", "Welcome Home"));
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void updateLocation(Context context) {
        locationTrack = new LocationTrack(this.context, views);
        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();

            if (LogLocationState.getLongitude() != longitude && LogLocationState.getLatitude() != latitude) {
                LogLocationState.setLongitude(longitude);
                LogLocationState.setLatitude(latitude);
                LogLocationState.getListLocation().add("longitude " + longitude + "\nlatidute " + latitude);
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String date = simpleDateFormat.format(new Date());
                    LogLocation.trace.add(new DataTraceModel(date, String.valueOf(latitude), String.valueOf(longitude), String.valueOf(locationTrack.getSpeed())));
                    countDistance(context);
                } catch (Exception e) {
                    Toast.makeText(context.getApplicationContext(), "Gagal Menyimpan Lokasi", Toast.LENGTH_SHORT).show();
                }
                views.setAdapter();
                Toast.makeText(context.getApplicationContext(), "Longitude:" + longitude + "\nLatitude:" + latitude, Toast.LENGTH_SHORT).show();
            }

        } else {
            locationTrack.showSettingsAlert();
        }
    }

    @Override
    public void countDistance(Context context) {
        LogView logView = new LogView();
        int indexLastTrace = LogLocationState.getListLocation().size();
        float[] distance = new float[1];
        DataTraceModel traceModel = LogLocation.trace.get((indexLastTrace - 1));

        /* Set EndLocation */
        Location.distanceBetween(Double.parseDouble(traceModel.getLatitude()), Double.parseDouble(traceModel.getLongitude()),
                -7.721467910488028, 113.50953702064095, distance);

        /* Set Radius */
        // double radiusInMeters = 20.0*1000.0; //1 KM = 1000 Meter
        double radiusInMeters = 10.0 * 1;

        if (distance[0] > radiusInMeters) {
            logView.showLog("Outside, distance from center: " + distance[0] + " radius: " + radiusInMeters);
        } else {
            logView.showLog("Inside, distance from center: " + distance[0] + " radius: " + radiusInMeters);
            sendTrance(context);

        }
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void sendTrance(Context context) {
        ProgressDialog dialog = ProgressDialog.show(context, "",
                "Mengirim data trace", true);
        dialog.show();

        LogView logView = new LogView();
        preferences = context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        String nis = preferences.getString("NIS", null);
        String tokenUser =  preferences.getString("token", null);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNow = simpleDateFormat.format(new Date());

        Call<InsertTraceModel> call = ApiClient.getInstance()
                .getApi()
                .insertTrace(tokenUser,nis, dateNow, LogLocation.trace);

        call.enqueue(new Callback<InsertTraceModel>() {
            @Override
            public void onResponse(@NonNull Call<InsertTraceModel> call, @NonNull final Response<InsertTraceModel> response) {
                logView.showLog(String.valueOf(response.code()));
                if (response.code() == 200) {
                    if((response.body() != null ? response.body().getStatus() : 1) == 0){
                        dialog.dismiss();
                        HomeState.setIsDoneTracing(true);
                        Intent toHome = new Intent(context, Home.class);
                        toHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(toHome);
                    }

                } else if (response.code() == 404) {
                    dialog.dismiss();
                    Toast.makeText(context, "Gagal Muat Data", Toast.LENGTH_LONG).show();
                } else if (response.code() == 401) {
                    dialog.dismiss();
                    Intent toHome = new Intent(context, Login.class);
                    toHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(toHome);
                } else if (response.code() == 500) {
                    dialog.dismiss();
                    Toast.makeText(context, "Terjadi Kesalahan Pada Server", Toast.LENGTH_LONG).show();
                }else {
                    dialog.dismiss();
                    Toast.makeText(context, "Jaringan Bermasalah", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<InsertTraceModel> call, @NonNull Throwable t) {
                dialog.dismiss();
                logView.showLog(t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
