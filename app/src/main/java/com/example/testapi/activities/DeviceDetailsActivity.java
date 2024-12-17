package com.example.testapi.activities;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.R;
import com.example.testapi.databinding.ActivityDeviceDetailsBinding;

public class DeviceDetailsActivity extends AppCompatActivity {

    ActivityDeviceDetailsBinding binding;
    private TextView nameTv,mobileTv,dateTv,modelTv,imei1Tv,imei2Tv,lastSyncTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDateofDeviceContent();
        binding.deviceTransiton.setVisibility(View.VISIBLE);

        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.tabDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateofDeviceContent();

            }
        });
        binding.tabReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContentView(R.layout.reminder_content);
                binding.deviceTransiton.setVisibility(View.GONE);
                binding.reminderTransiton.setVisibility(View.VISIBLE);
                binding.agreementTransiton.setVisibility(View.GONE);
            }
        });
        binding.tabAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContentView(R.layout.agreement_content);
                binding.deviceTransiton.setVisibility(View.GONE);
                binding.reminderTransiton.setVisibility(View.GONE);
                binding.agreementTransiton.setVisibility(View.VISIBLE);
            }
        });
    }


    private void loadContentView(int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(layoutId, null);

        // Replace the content in the FrameLayout
        binding.frameLayout.setVisibility(View.GONE);
        binding.frameLayout.setVisibility(View.VISIBLE);
        binding.frameLayout.removeAllViews();
        binding.frameLayout.addView(contentView);
    }
    private void getDateofDeviceContent(){
        loadContentView(R.layout.device_content);
        binding.deviceTransiton.setVisibility(View.VISIBLE);
        binding.reminderTransiton.setVisibility(View.GONE);
        binding.agreementTransiton.setVisibility(View.GONE);
        String imei1 = getIntent().getStringExtra("imei1");
        String imei2 = getIntent().getStringExtra("imei2");
        String mobile = getIntent().getStringExtra("mobile");
        String name = getIntent().getStringExtra("name");
        String date = getIntent().getStringExtra("downPayDate");
        String model = getIntent().getStringExtra("model");
        String lastSync = getIntent().getStringExtra("lastSync");
        nameTv=findViewById(R.id.customerNameTV);
        mobileTv=findViewById(R.id.customerMobileTV);
        imei1Tv=findViewById(R.id.imei1TV);
        imei2Tv=findViewById(R.id.imei2TV);
        dateTv=findViewById(R.id.dateTV);
        modelTv=findViewById(R.id.modelTV);
        lastSyncTv=findViewById(R.id.lastSyncTV);

        nameTv.setText(name);
        imei1Tv.setText("IMEI1 : "+imei1);
        imei2Tv.setText("IMEI2 : "+imei2);
        mobileTv.setText(mobile);
        dateTv.setText(date);
        modelTv.setText("Model : "+model);
        lastSyncTv.setText("Last Sync : "+lastSync);
    }

}