package com.example.testapi.activities;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityDeviceDetailsBinding;
import com.example.testapi.models.EmiScheduleModel;
import com.example.testapi.models.InstallmentAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceDetailsActivity extends AppCompatActivity {

    ActivityDeviceDetailsBinding binding;
    InstallmentAdapter installmentAdapter;
    RecyclerView reminderRecView;

    private TextView nameTv,mobileTv,dateTv,modelTv,imei1Tv,imei2Tv,lastSyncTv,totalDefaultedAmountTv,defaultedDateTv,remainingToPayTv,reminderImeiTv;
    private Button payBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.deviceTransiton.setVisibility(View.VISIBLE);

        getDataOfDeviceContent();


        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.tabDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataOfDeviceContent();
            }
        });
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceDetailsActivity.this,PaymentActivity.class));
            }
        });
        binding.tabReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContentView(R.layout.reminder_content);
                binding.deviceTransiton.setVisibility(View.GONE);
                binding.reminderTransiton.setVisibility(View.VISIBLE);
                binding.agreementTransiton.setVisibility(View.GONE);
                getDataOfReminderContent();
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
        binding.frameLayout.removeAllViews();
        binding.frameLayout.addView(contentView);
        initializeViews(contentView);
    }
    private void getDataOfDeviceContent(){
        loadContentView(R.layout.device_content);
        binding.deviceTransiton.setVisibility(View.VISIBLE);
        binding.reminderTransiton.setVisibility(View.GONE);
        binding.agreementTransiton.setVisibility(View.GONE);

        initializeViews(binding.frameLayout.getChildAt(0));

        SharedPreferences sp = getSharedPreferences("defaulters", Context.MODE_PRIVATE);
        String isDefaulter = sp.getString("isDefaulter","");
        if("yes".equals(isDefaulter)){
            modelTv.setVisibility(View.GONE);
            lastSyncTv.setVisibility(View.GONE);
            totalDefaultedAmountTv.setVisibility(View.VISIBLE);
            defaultedDateTv.setVisibility(View.VISIBLE);
            remainingToPayTv.setVisibility(View.VISIBLE);

            String defaulterImei1 = getIntent().getStringExtra("defaulterImei1");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("defaulterImei1",defaulterImei1);
            editor.apply();
            String defaulterImei2 = getIntent().getStringExtra("defaulterImei2");
            String defaulterName = getIntent().getStringExtra("defaulterName");
            String defaulterMobile = getIntent().getStringExtra("defaulterMobile");
            int totalDefaultedAmount = getIntent().getIntExtra("totalDefaultedAmount",0);
            String defaulterDownPaymentDate = getIntent().getStringExtra("defaulterDownPaymentDate");
            String defaultedDate = getIntent().getStringExtra("defaultedDate");
            int remainingToPay = getIntent().getIntExtra("remainingToPay",0);
            nameTv.setText(defaulterName);
            imei1Tv.setText("IMEI1 : "+defaulterImei1);
            imei2Tv.setText("IMEI2 : "+defaulterImei2);
            mobileTv.setText(defaulterMobile);
            dateTv.setText("Defaulted on : "+defaultedDate);
            totalDefaultedAmountTv.setText("Total Defaulted Amount : "+totalDefaultedAmount);
            defaultedDateTv.setText("Defaulted downPay date : "+defaulterDownPaymentDate);
            remainingToPayTv.setText("Remaining To Pay : "+remainingToPay);
        }else if("no".equals(isDefaulter)){
            modelTv.setVisibility(View.VISIBLE);
            lastSyncTv.setVisibility(View.VISIBLE);
            totalDefaultedAmountTv.setVisibility(View.GONE);
            defaultedDateTv.setVisibility(View.GONE);
            remainingToPayTv.setVisibility(View.GONE);
            String imei1 = getIntent().getStringExtra("imei1");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("imei1",imei1);
            editor.apply();
            String imei2 = getIntent().getStringExtra("imei2");
            String mobile = getIntent().getStringExtra("mobile");
            String name = getIntent().getStringExtra("name");
            String date = getIntent().getStringExtra("downPayDate");
            String model = getIntent().getStringExtra("model");
            String lastSync = getIntent().getStringExtra("lastSync");
            nameTv.setText(name);
            imei1Tv.setText("IMEI1 : "+imei1);
            imei2Tv.setText("IMEI2 : "+imei2);
            mobileTv.setText(mobile);
            dateTv.setText("Down pay date : "+date);
            modelTv.setText("Model : "+model);
            lastSyncTv.setText("Last Sync : "+lastSync);

        }

    }
    private void getDataOfReminderContent(){
        loadContentView(R.layout.reminder_content);
        binding.deviceTransiton.setVisibility(View.GONE);
        binding.reminderTransiton.setVisibility(View.VISIBLE);
        binding.agreementTransiton.setVisibility(View.GONE);
        initializeViews(binding.frameLayout.getChildAt(0));
        SharedPreferences sp = getSharedPreferences("defaulters", Context.MODE_PRIVATE);
        String isDefaulter = sp.getString("isDefaulter","");
        if("yes".equals(isDefaulter)){
            String defaulterImei1 = getIntent().getStringExtra("defaulterImei1");
            //call reminder api according to defaulter imei1
            getSchedule(defaulterImei1);

        }else if("no".equals(isDefaulter)){
            String imei1 = getIntent().getStringExtra("imei1");
            //call reminder api according to imei1
            getSchedule(imei1);
        }

    }

    private void getSchedule(String imei1){
        reminderImeiTv.setText("Installments for IMEI1 ( "+imei1+" ) scheduled below :");
        Call<EmiScheduleModel> call = ApiController
                .getInstance()
                .getapi()
                .getEmiSchedule(imei1);

        call.enqueue(new Callback<EmiScheduleModel>() {
            @Override
            public void onResponse(Call<EmiScheduleModel> call, Response<EmiScheduleModel> response) {
                if(response.isSuccessful() && response.body()!=null){
                    String msg = response.body().getMessage();
                    if("EMI schedule retrieved successfully".equals(msg)){
                        Toast.makeText(DeviceDetailsActivity.this, "emi schedule retrieved successfully", Toast.LENGTH_SHORT).show();
                        List<EmiScheduleModel.EmiSchedule> emiScheduleList = response.body().getEmiScheduleList();
                        installmentAdapter = new InstallmentAdapter(emiScheduleList);
                        reminderRecView.setAdapter(installmentAdapter);
                    }
                }else{
                    Toast.makeText(DeviceDetailsActivity.this, "emi schedule response body is null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<EmiScheduleModel> call, Throwable t) {
                Toast.makeText(DeviceDetailsActivity.this, "Failed to fetch EMI schedule", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initializeViews(View view) {
        nameTv = findViewById(R.id.customerNameTV);
        mobileTv = findViewById(R.id.customerMobileTV);
        imei1Tv = findViewById(R.id.imei1TV);
        imei2Tv = findViewById(R.id.imei2TV);
        dateTv = findViewById(R.id.dateTV);
        modelTv = findViewById(R.id.modelTV);
        lastSyncTv = findViewById(R.id.lastSyncTV);
        totalDefaultedAmountTv = findViewById(R.id.totalDefaultedAmountTV);
        defaultedDateTv = findViewById(R.id.defaultedDateTV);
        remainingToPayTv = findViewById(R.id.remainingToPayTV);
        reminderImeiTv = findViewById(R.id.reminderImeiTv);
        reminderRecView = view.findViewById(R.id.reminderRecView);
        if (reminderRecView != null) {
            reminderRecView.setLayoutManager(new LinearLayoutManager(this));
        }
        payBtn = findViewById(R.id.paymentBtn);
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (binding.deviceTransiton.getVisibility() == View.VISIBLE) {
//            getDataOfDeviceContent(); // Refresh device content
//        } else if (binding.reminderTransiton.getVisibility() == View.VISIBLE) {
//            getDataOfReminderContent(); // Refresh reminder content
//        }
//    }
}