package com.example.absenlocation.State;

public class LoginState {
    static String email;
    static String password;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String nis) {
        LoginState.email = nis;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        LoginState.password = password;
    }
}
