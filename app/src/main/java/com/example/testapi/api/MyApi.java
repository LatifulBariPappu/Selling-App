package com.example.testapi.api;

import com.example.testapi.models.RetailerModel;
import com.example.testapi.models.model;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApi {
    //distributor login api
    @POST("api/distributors/login")
    Call<model> distributorLogin(
            @Query("mobile") String mobile,
            @Query("password") String password
    );
    //retailer login api
    @POST("/api/retail/login")
    Call<RetailerModel> retailerLogin(
            @Query("mobile") String mobile,
            @Query("password") String password
    );

}
