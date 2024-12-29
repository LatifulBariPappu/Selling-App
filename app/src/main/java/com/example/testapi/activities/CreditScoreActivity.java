package com.example.testapi.activities;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testapi.databinding.ActivityCreditScoreBinding;

public class CreditScoreActivity extends AppCompatActivity {

    ActivityCreditScoreBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCreditScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backCreditScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}