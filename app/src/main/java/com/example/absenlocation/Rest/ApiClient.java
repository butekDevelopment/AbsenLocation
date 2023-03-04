package com.example.absenlocation.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiClient mInstance;
    private final Retrofit retrofit;

    private ApiClient(String url){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://sman1paiton.online/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiClient getInstance() {
        if (mInstance == null) {
            mInstance = new ApiClient("https://sman1paiton.online/api/");
        }
        return mInstance;
    }

    public EndPoint getApi(){
        return retrofit.create(EndPoint.class);
    }

}
