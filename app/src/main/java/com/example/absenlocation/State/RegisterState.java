package com.example.absenlocation.State;

public class RegisterState {
    static String nis;
    static String password;
    static String repeatPassword;

    public static String getNis() {
        return nis;
    }

    public static void setNis(String nis) {
        RegisterState.nis = nis;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        RegisterState.password = password;
    }

    public static String getRepeatPassword() {
        return repeatPassword;
    }

    public static void setRepeatPassword(String repeatPassword) {
        RegisterState.repeatPassword = repeatPassword;
    }
}
