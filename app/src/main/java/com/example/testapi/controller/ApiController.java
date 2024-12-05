package com.example.testapi.controller;

import com.example.testapi.api.MyApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {
    private static final String baseUrl="http://192.168.0.218:8000/";
    private static ApiController clientobj;
    private static Retrofit retrofit;

    ApiController(){
        retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized ApiController getInstance(){
        if(clientobj ==null){
            clientobj =new ApiController();
        }
        return clientobj;
    }
    public MyApi getapi(){
        return retrofit.create(MyApi.class);
    }
}
