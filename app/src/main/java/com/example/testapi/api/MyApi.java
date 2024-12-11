package com.example.testapi.api;

import com.example.testapi.models.DeviceModel;
import com.example.testapi.models.RetailerModel;
import com.example.testapi.models.SaleRequestModel;
import com.example.testapi.models.SaleResponseModel;
import com.example.testapi.models.DistributorModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApi {
    //distributor login api
    @POST("distributors/login")
    Call<DistributorModel> distributorLogin(
            @Query("mobile") String mobile,
            @Query("password") String password
    );
    //retailer login api
    @POST("retail/login")
    Call<RetailerModel> retailerLogin(
            @Query("mobile") String mobile,
            @Query("password") String password
    );
    //device model api
    @POST("devicemodel/imei")
    Call<DeviceModel> getDevice(
            @Query("imei") String imei
    );
    //hire sale price api
    @POST("hire-sale")
    Call<SaleResponseModel> sendRequest(
            @Header("Authorization") String authorization,
            @Body SaleRequestModel request
            );
}
