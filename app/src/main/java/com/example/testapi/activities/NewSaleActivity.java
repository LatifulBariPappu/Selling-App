package com.example.testapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
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
    private void addDevice(String imei, int retailerId) {
        binding.imeiProgress.setVisibility(View.VISIBLE);
        binding.imeiInfoBtn.setVisibility(View.GONE);

        Call<AddDevice> call = ApiController
                .getInstance()
                .getapi()
                .addDevice(imei, retailerId);

        call.enqueue(new Callback<AddDevice>() {
            @Override
            public void onResponse(Call<AddDevice> call, Response<AddDevice> response) {
                binding.imeiProgress.setVisibility(View.GONE);
                binding.imeiInfoBtn.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    AddDevice responseBody = response.body();
                    String message = responseBody.getMessage();
                    String status = responseBody.getStatus();

                    switch (message) {
                        case "Device Added Successfully":
                            Toast.makeText(getApplicationContext(), "Device Added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NewSaleActivity.this, SaleCustomerInfoActivity.class));
                            break;

                        case "Validation errors":
                            if (responseBody.getErrors() != null && responseBody.getErrors().containsKey("imei")) {
                                String errorMsg = responseBody.getErrors().get("imei")[0];
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Validation errors occurred.", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case "Add request not confirmed":
                            Toast.makeText(getApplicationContext(), "Add request not confirmed", Toast.LENGTH_SHORT).show();
                            break;

                        case "Retailer not found":
                            Toast.makeText(getApplicationContext(), "Retailer not found", Toast.LENGTH_SHORT).show();
                            break;

                        case "Error message":
                            Toast.makeText(getApplicationContext(), "Error occurred: Error message", Toast.LENGTH_SHORT).show();
                            break;

                        case "Invalid response from confirmation service":
                            Toast.makeText(getApplicationContext(), "Invalid response from confirmation service", Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            Toast.makeText(getApplicationContext(), "Unexpected response: " + message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Log.d("addDevice2", "Response failed: " + response.message());
                    Toast.makeText(getApplicationContext(), "Response failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddDevice> call, Throwable t) {
                Log.d("addDevice", t.getMessage());
                Toast.makeText(getApplicationContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.imeiProgress.setVisibility(View.GONE);
                binding.imeiInfoBtn.setVisibility(View.VISIBLE);
            }
        });
    }


}