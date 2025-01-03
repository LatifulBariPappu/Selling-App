package com.example.testapi.api;

import com.example.testapi.models.AddDevice;
import com.example.testapi.models.DeviceListModel;
import com.example.testapi.models.EmiScheduleModel;
import com.example.testapi.models.GoodCustomerModel;
import com.example.testapi.models.HappyCustomerModel;
import com.example.testapi.models.HistoryModel;
import com.example.testapi.models.LockDeviceModel;
import com.example.testapi.models.PaymentModel;
import com.example.testapi.models.PaymentRequest;
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
    @POST("payment")
    Call<PaymentModel> getPayment(
            @Header("Authorization") String authorization,
            @Body PaymentRequest request
    );
    @POST("emischedule/imei")
    Call<EmiScheduleModel> getEmiSchedule(
            @Query("imei") String imei
    );
    @POST("add-device")
    Call<AddDevice> addDevice(
            @Query("imei") String imei,
            @Query("retail_id") int retail_id
    );

    @POST("payment-history")
    Call<HistoryModel> getPaymentHistory(
            @Query("imei") String imei
    );
    @POST("happy-customers")
    Call<HappyCustomerModel> getHappyCustomers(
            @Query("retail_id") int retail_id);
    @POST("good-customers")
    Call<GoodCustomerModel> getGoodCustomers(
            @Query("retail_id") int retail_id);

}