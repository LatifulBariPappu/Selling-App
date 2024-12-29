package com.example.testapi.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testapi.databinding.ActivityCustomerDetailsBinding;

public class CustomerDetailsActivity extends AppCompatActivity {

    ActivityCustomerDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    }
}