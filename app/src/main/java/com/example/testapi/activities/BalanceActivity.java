package com.example.testapi.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testapi.R;
import com.example.testapi.databinding.ActivityBalanceBinding;
import com.example.testapi.databinding.ActivityCustomersListBinding;

public class BalanceActivity extends AppCompatActivity {

    ActivityBalanceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBalanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}