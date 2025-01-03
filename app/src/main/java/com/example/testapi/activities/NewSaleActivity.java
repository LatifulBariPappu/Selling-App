package com.example.testapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivitySaleBinding;
import com.example.testapi.models.AddDevice;
import com.example.testapi.models.CaptureAct;
import java.util.Objects;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewSaleActivity extends AppCompatActivity {
    ActivitySaleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imeiEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});

        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.imeiEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 15) {
                    binding.imeiEdt.setError(null);
                    binding.imeiInfoBtn.setVisibility(View.VISIBLE);
                    binding.barcodeBtn.setVisibility(View.GONE);
                }else {
                    binding.imeiEdt.setError("IMEI must be exactly 15 characters long");
                    binding.barcodeBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imeiEdt.setText("");
                scancode();
            }
        });

        binding.imeiInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imeiInput = Objects.requireNonNull(binding.imeiEdt.getText()).toString();
                SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
                int retailerId = sp.getInt("retailerId",0);
                addDevice(imeiInput,retailerId);
            }
        });
    }

    private void scancode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(),result->{
        if(result.getContents() != null){
            binding.imeiEdt.setText(result.getContents());
        }
    });
    private void addDevice(String imei,int retailerId){
        binding.imeiProgress.setVisibility(View.VISIBLE);
        binding.imeiInfoBtn.setVisibility(View.GONE);
        Call<AddDevice> call = ApiController
                .getInstance()
                .getapi()
                .addDevice(imei,retailerId);
        call.enqueue(new Callback<AddDevice>() {
            @Override
            public void onResponse(Call<AddDevice> call, Response<AddDevice> response) {
                binding.imeiProgress.setVisibility(View.GONE);
                binding.imeiInfoBtn.setVisibility(View.VISIBLE);
                if(response.isSuccessful() && response.body()!=null){
                    String msg = response.body().getMessage();
                    if("Device Added Successfully".equals(msg)){
                        Toast.makeText(getApplicationContext(), "Device Added Successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("add_device",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("imei",imei);
                        editor.apply();
                        startActivity(new Intent(NewSaleActivity.this,SaleCustomerInfoActivity.class));
                    }else if("Validation errors".equals(msg)){
                        Toast.makeText(getApplicationContext(), "The IMEI field is required.", Toast.LENGTH_SHORT).show();
                    }else if("Add request not confirmed".equals(msg)){
                        Toast.makeText(getApplicationContext(), "Add request not confirmed", Toast.LENGTH_SHORT).show();
                    }else if("Retailer not found".equals(msg)){
                        Toast.makeText(getApplicationContext(), "Retailer not found", Toast.LENGTH_SHORT).show();
                    }else if("Error message".equals(msg)){
                        Toast.makeText(getApplicationContext(), "Error message", Toast.LENGTH_SHORT).show();
                    }else if("Invalid response from confirmation service".equals(msg)){
                        Toast.makeText(getApplicationContext(), "Invalid response from confirmation service", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "response body is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddDevice> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed: " + t.getMessage(),Toast.LENGTH_SHORT).show();
                binding.imeiProgress.setVisibility(View.GONE);
                binding.imeiInfoBtn.setVisibility(View.VISIBLE);
            }
        });
    }

}