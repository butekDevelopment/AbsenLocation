package com.example.absenlocation.Model.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("nis")
    @Expose
    private String nis;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("today tracing")
    @Expose
    private TodayTracingModel todayTracingModel;

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TodayTracingModel getTodayTracing() {
        return todayTracingModel;
    }

    public void setTodayTracing(TodayTracingModel todayTracingModel) {
        this.todayTracingModel = todayTracingModel;
    }

}