package com.example.testapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {
    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkexistence();

        binding.logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
                sp.edit().remove("logged").apply();
                startActivity(new Intent(getApplicationContext(), FirstActivity.class));
                finish();
            }
        });

    }
    private void checkexistence() {
        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
        if(sp.contains("logged")) {
            String userCategory = sp.getString("logged", "");
            binding.textView.setText("Welcome " + userCategory);
        }
    }
}