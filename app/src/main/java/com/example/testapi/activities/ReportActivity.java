package com.example.testapi.activities;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testapi.databinding.ActivityReportBinding;

public class ReportActivity extends AppCompatActivity {

    ActivityReportBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}