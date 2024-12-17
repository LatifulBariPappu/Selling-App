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
import com.example.testapi.databinding.ActivityInvoiceBinding;

public class InvoiceActivity extends AppCompatActivity {
    ActivityInvoiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InvoiceActivity.this,RetailerHomeActivity.class));
            }
        });

        SharedPreferences sp = getSharedPreferences("device_details",MODE_PRIVATE);
        SharedPreferences sp2 = getSharedPreferences("saved_login",MODE_PRIVATE);

        String customerName = sp.getString("customerName","");
        String customerMobile = sp.getString("customerMobile","");
        String customerAddress = sp.getString("customerAddress","");
        String posInvoiceNumber = sp.getString("posInvoiceNumber","");
        String downPaymentDate = sp.getString("downPaymentDate","");
        String imei1 = sp.getString("imei1","");
        String barcode = sp.getString("barcode","");
        String serial = sp.getString("serial","");
        String model = sp.getString("model","");
        String brand = sp.getString("brand","");
        String color  = sp.getString("color ","");
        int hireSalePrice = sp.getInt("hireSalePrice",0);
        int numberOfInstallment = sp.getInt("numberOfInstallment",0);
        int downPayment = sp.getInt("downPayment",0);
        int duePayment = hireSalePrice - downPayment;

        String retailerId = String.valueOf(sp2.ge)


    }
}