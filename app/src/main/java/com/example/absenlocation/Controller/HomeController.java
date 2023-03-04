package com.example.absenlocation.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.absenlocation.Activity.Home;
import com.example.absenlocation.Activity.Login;
import com.example.absenlocation.Interface.HomeInterface;
import com.example.absenlocation.Model.Profile.ProfileModel;
import com.example.absenlocation.Rest.ApiClient;
import com.example.absenlocation.State.HomeState;
import com.example.absenlocation.Utils.LogView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeController implements HomeInterface.Controller{

    Context context;
    SharedPreferences preferences;
    HomeInterface.Views views;

    public HomeController(Home home, HomeInterface.Views views){
        this.context = home;
        this.views = views;
    }

    @Override
    public void getDataProfile() {
        LogView logView = new LogView();
        preferences = context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        String tokenUser =  preferences.getString("token", null);

        ProgressDialog dialog = ProgressDialog.show(context, "",
                "Memuat Data", true);
        dialog.show();

        Call<ProfileModel> call = ApiClient.getInstance()
                .getApi()
                .getProfile(tokenUser);


        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ProfileModel> call, @NonNull final Response<ProfileModel> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    if(response.body() != null){
                        HomeState.setIndukSiswa(response.body().getNis());
                        HomeState.setNamaSiswa(response.body().getName());
                        HomeState.setEmailSiswa(response.body().getEmail());
                        if(response.body().getTodayTracing() != null){
                            HomeState.setIsDoneTracing(true);
                        }
                    }else{
                        HomeState.setIndukSiswa("No NIS");
                        HomeState.setNamaSiswa("No Name");
                        HomeState.setEmailSiswa("No Email");
                        HomeState.setIsDoneTracing(false);
                    }
                    views.updateViews();

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
            public void onFailure(@NonNull Call<ProfileModel> call, @NonNull Throwable t) {
                dialog.dismiss();
                logView.showLog(t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void logOut(Context context) {
        SharedPreferences prefsDataUser = context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        prefsDataUser.edit().clear().apply();
        SharedPreferences prefsLogin = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        prefsLogin.edit().clear().apply();

        Intent toLogin = new Intent(context, Login.class);
        toLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(toLogin);
    }
}
