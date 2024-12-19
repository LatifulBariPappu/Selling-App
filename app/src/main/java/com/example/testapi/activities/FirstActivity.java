package com.example.testapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.testapi.databinding.ActivityFirstBinding;

public class FirstActivity extends AppCompatActivity{

    ActivityFirstBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        checkExistence();

        binding.distributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("login_as",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user","Distributor");
                editor.apply();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        binding.retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("login_as",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user","Retailer");
                editor.apply();
                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
            }
        });

    }
    private void checkExistence() {
        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
        String userCategory = sp.getString("logged","");
        if(userCategory.equals("Distributor")){
            startActivity(new Intent(FirstActivity.this, DistributorHomeActivity.class));
            finish();
        }
        if(userCategory.equals("Retailer")){
            startActivity(new Intent(FirstActivity.this, RetailerHomeActivity.class));
            finish();
        }
    }
}