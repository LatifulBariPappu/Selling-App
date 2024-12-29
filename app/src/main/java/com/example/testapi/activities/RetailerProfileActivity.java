package com.example.testapi.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testapi.databinding.ActivityRetailerProfileBinding;

public class RetailerProfileActivity extends AppCompatActivity {
    ActivityRetailerProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetailerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
        int retailerId = sp.getInt("retailerId",0);
        String retailerName =sp.getString("retailerName","");
        String retailerMobile =sp.getString("retailerMobile","");
        String retailerAddress =sp.getString("retailerAddress","");
        String retailDistributorName =sp.getString("retailDistributorName","");

        binding.retailerIdTv.setText(String.valueOf(retailerId));
        binding.retailerNameTv.setText(retailerName);
        binding.retailerMobileTv.setText(retailerMobile);
        binding.retailerAddressTv.setText(retailerAddress);
        binding.retailerDistributorNameTv.setText(retailDistributorName);
    }
}