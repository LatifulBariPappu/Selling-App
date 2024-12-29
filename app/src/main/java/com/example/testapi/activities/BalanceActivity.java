package com.example.testapi.activities;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testapi.databinding.ActivityBalanceBinding;
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