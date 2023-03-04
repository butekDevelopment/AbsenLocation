package com.example.absenlocation.State;

public class HomeState {
    static String indukSiswa;
    static String namaSiswa;
    static String emailSiswa;
    static boolean isDoneTracing = false;

    public static boolean isIsDoneTracing() {
        return isDoneTracing;
    }

    public static void setIsDoneTracing(boolean isDoneTracing) {
        HomeState.isDoneTracing = isDoneTracing;
    }

    public static String getIndukSiswa() {
        return indukSiswa;
    }

    public static void setIndukSiswa(String indukSiswa) {
        HomeState.indukSiswa = indukSiswa;
    }

    public static String getNamaSiswa() {
        return namaSiswa;
    }

    public static void setNamaSiswa(String namaSiswa) {
        HomeState.namaSiswa = namaSiswa;
    }

    public static String getEmailSiswa() {
        return emailSiswa;
    }

    public static void setEmailSiswa(String emailSiswa) {
        HomeState.emailSiswa = emailSiswa;
    }

}
