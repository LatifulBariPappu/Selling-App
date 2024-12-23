package com.example.testapi.api;

import com.example.testapi.models.DeviceListModel;
import com.example.testapi.models.DeviceModel;
import com.example.testapi.models.EmiScheduleModel;
import com.example.testapi.models.LockDeviceModel;
import com.example.testapi.models.RetailerModel;
import com.example.testapi.models.SaleRequestModel;
import com.example.testapi.models.SaleResponseModel;
import com.example.testapi.models.DistributorModel;
import com.example.testapi.models.DefaulterResponse;

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
    //device lists api
    @POST("retail/device-list")
    Call<DeviceListModel> getDeviceLists(
            @Query("retail_id") int retail_id
    );
    //lock device api
    @POST("lock-device-by-retail")
    Call<LockDeviceModel> lockDevice(
            @Query("imei1") String imei1
    );

    //defaulters list api
    @POST("defaulter-list")
    Call<DefaulterResponse> getDefaulterList(
            @Query("retail_id") int retail_id
    );
    @POST("emischedule")
    Call<EmiScheduleModel> getEmiSchedule(
            @Query("imei") String imei
    );

}