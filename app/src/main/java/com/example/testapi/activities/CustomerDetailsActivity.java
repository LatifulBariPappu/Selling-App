package com.example.testapi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityCustomerDetailsBinding;
import com.example.testapi.models.HistoryAdapter;
import com.example.testapi.models.HistoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity {

    ActivityCustomerDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.customerPaymentRecView.setLayoutManager(new LinearLayoutManager(this));

        binding.backCustomerDetail.setOnClickListener(v -> {
            finish();
        });
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        binding.customerDetailNameTv.setText(name);
        String mobile = intent.getStringExtra("mobile");
        binding.customerDetailMobileTv.setText(mobile);
        String id = intent.getStringExtra("id");
        binding.customerDetailIdTv.setText("Id : "+id);
        long nid = intent.getLongExtra("nid",0);
        binding.customerDetailNidTv.setText("NID : "+nid);
        String address = intent.getStringExtra("address");
        binding.customerDetailAddressTv.setText("Address : "+address);

        String imei1 = intent.getStringExtra("imei1");
        binding.customerDetailImei1Tv.setText("IMEI1 : "+imei1);
        String imei2 = intent.getStringExtra("imei2");
        binding.customerDetailImei2Tv.setText("IMEI2 : "+imei2);

        String serial = intent.getStringExtra("serial");
        binding.customerDetailSerialTv.setText("Serial : "+serial);

        String model = intent.getStringExtra("model");
        binding.customerDetailModelTv.setText("Model : "+model);
        String brand = intent.getStringExtra("brand");
        binding.customerDetailBrandTv.setText("Brand : "+brand);
        String color = intent.getStringExtra("color");
        binding.customerDetailColorTv.setText("Color : "+color);
        String lastSync = intent.getStringExtra("lastSync");
        binding.lastSyncTV.setText("Last Sync : "+lastSync);
        String downPayDate = intent.getStringExtra("downPayDate");
        binding.customerDetailDateTv.setText("Down pay date\n"+downPayDate);
        int totalPayment = intent.getIntExtra("totalPayment",0);
        binding.customerDetailTotalPaymentTV.setText("Total Payment : "+totalPayment);
        int totalDue = intent.getIntExtra("totalDue",0);
        binding.customerDetailTotalDueTV.setText("Total Due : "+totalDue);
        
        getPayHistory(imei1);

    }
    
    private void getPayHistory(String imei){
        Call<HistoryModel> call =
                ApiController.getInstance()
                        .getapi().getPaymentHistory(imei);
        call.enqueue(new Callback<HistoryModel>() {
            @Override
            public void onResponse(Call<HistoryModel> call, Response<HistoryModel> response) {
                if (response.isSuccessful() && response.body() != null){
                    String msg = response.body().getMessage();
                    if("Payment history retrieved successfully.".equals(msg)){
                        Toast.makeText(getApplicationContext(), "Payment history retrieved successfully.", Toast.LENGTH_SHORT).show();
                        List<HistoryModel.data> data =response.body().getData();
                        binding.customerPaymentRecView.setAdapter(new HistoryAdapter(data));
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Payment history response body is null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<HistoryModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        
    }
}