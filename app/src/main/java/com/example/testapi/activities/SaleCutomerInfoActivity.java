package com.example.testapi.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.databinding.ActivitySaleCutomerInfoBinding;

public class SaleCutomerInfoActivity extends AppCompatActivity {

    ActivitySaleCutomerInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaleCutomerInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





    }
}