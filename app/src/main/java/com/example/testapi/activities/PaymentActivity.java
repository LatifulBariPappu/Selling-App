package com.example.testapi.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {
    ActivityPaymentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setOnClickListener(v -> finish());

        SharedPreferences sp = getSharedPreferences("defaulters", Context.MODE_PRIVATE);
        String isDefaulter = sp.getString("isDefaulter","");
        if("yes".equals(isDefaulter)){
            String defaulterImei1 = sp.getString("defaulterImei1","") ;
            binding.paymentImeiTv.setText("Make payment for IMEI1 ( "+defaulterImei1+" )");

        }else if("no".equals(isDefaulter)){
            String imei1 = sp.getString("imei1","");
            binding.paymentImeiTv.setText("Make payment for IMEI1 ( "+imei1+" )");

        }

    }
}