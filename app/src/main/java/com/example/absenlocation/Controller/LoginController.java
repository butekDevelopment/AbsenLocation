package com.example.absenlocation.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;

import com.example.absenlocation.Activity.Home;
import com.example.absenlocation.Activity.LogLocation;
import com.example.absenlocation.Activity.Login;
import com.example.absenlocation.Interface.LoginInterface;
import com.example.absenlocation.Model.LoginModel;
import com.example.absenlocation.Rest.ApiClient;
import com.example.absenlocation.State.LoginState;
import com.example.absenlocation.Utils.LogView;
import com.example.absenlocation.Utils.Message.MessageController;
import com.example.absenlocation.Utils.Message.MessageInterface;
import com.google.android.material.button.MaterialButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController implements LoginInterface.Controller {
    Context context;
    LoginInterface.Views views;
    MessageInterface.Controller messagePresenter;
    SharedPreferences preferencesLogin, preferencesToken;

    public LoginController(Login login, LoginInterface.Views views) {
        this.context = login;
        this.views = views;
    }

    @Override
    public void userIsLogin(Context context) {
        preferencesLogin = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        boolean userIsLogin =  preferencesLogin.getBoolean("login", false);
        if(userIsLogin){
            Intent toHome = new Intent(context, Home.class);
            toHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(toHome);
        }
    }

    @Override
    public boolean checkInputan(Context context, View contextView) {
        if (LoginState.getEmail().isEmpty() || LoginState.getPassword().isEmpty()) {
            views.errorInput();
            return false;
        } else {
            views.errorInput();
            return true;
        }

    }

    @Override
    public void doLogin(Context context, View contextView, MaterialButton btnLogin, ProgressBar loading) {
        LogView logView = new LogView();
        messagePresenter = new MessageController(context);
        Call<LoginModel> call = ApiClient.getInstance()
                .getApi()
                .doLogin(LoginState.getEmail(), LoginState.getPassword());

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull final Response<LoginModel> response) {
                if (response.code() == 200) {
                    messagePresenter.SnackbarSuccesss("Berhasil untuk login ", contextView);
                    succeedLogin(btnLogin, loading);

                    new CountDownTimer(400, 1000) {
                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            removeSharedPrefences(context);

                            preferencesLogin = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferencesLogin.edit();
                            editor.putBoolean("login", true);
                            editor.apply();

                            preferencesToken = context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorToken = preferencesToken.edit();
                            if (response.body() != null) {
                                editorToken.putString("NIS", response.body().getNis());
                                editorToken.putString("messageWelcome", response.body().getMessage());
                                editorToken.putString("token", "Bearer " + response.body().getAccessToken());
                            }
                            editorToken.apply();

                            Intent toHome = new Intent(context, Home.class);
                            toHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(toHome);
                        }
                    }.start();
                } else if (response.code() == 404) {
                    messagePresenter.SnackbarWarning((response.body() != null ? response.body().getMessage() : null) != null ? response.body().getMessage() : "Gagal Melakukan Login", contextView);
                    hideLoading(btnLogin, loading);
                } else if (response.code() == 500) {
                    messagePresenter.SnackbarError("Terjadi Kesalahan Pada Server", contextView);
                    hideLoading(btnLogin, loading);
                } else {
                    messagePresenter.SnackbarWarning("Jaringan Bermasalah", contextView);
                    hideLoading(btnLogin, loading);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                logView.showLog(t.getMessage());
                hideLoading(btnLogin, loading);
                messagePresenter.SnackbarError("Tidak terhubung dengan server", contextView);

            }
        });
    }

    @Override
    public void removeSharedPrefences(Context context) {
        context.getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("'dataToken'", Context.MODE_PRIVATE).edit().clear().apply();
    }

    public void hideLoading(MaterialButton btnLogin, ProgressBar loading) {
        btnLogin.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    public void succeedLogin(MaterialButton btnLogin, ProgressBar loading) {
        btnLogin.setVisibility(View.GONE);
        loading.setVisibility(View.INVISIBLE);
    }
}
