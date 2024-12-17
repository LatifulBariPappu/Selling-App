package com.example.testapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testapi.R;
import com.example.testapi.databinding.ActivityDeviceInfoBinding;

public class DeviceInfoActivity extends AppCompatActivity {

    ActivityDeviceInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sp = getSharedPreferences("device_details",MODE_PRIVATE);
        String imei1 = sp.getString("imei1","");
        String imei2 = sp.getString("barcode","");
        String model = sp.getString("model","");
        String brand = sp.getString("brand","");
        String color = sp.getString("color","");
        int price = sp.getInt("price",0);
        int distributorId = sp.getInt("distributorId",0);
        String distributorName = sp.getString("distributorName","");

        binding.imeiTv1.setText(imei1);
        binding.imeiTv2.setText(imei2);
        binding.modelTv.setText(model);
        binding.brandTv.setText(brand);
        binding.colorTv.setText(color);
        binding.priceTv.setText(String.valueOf(price));
        binding.distributorIdTv.setText(String.valueOf(distributorId));
        binding.distributorTv.setText(distributorName);

        binding.foundToNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceInfoActivity.this, SaleCutomerInfoActivity.class));
            }
        });
        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}