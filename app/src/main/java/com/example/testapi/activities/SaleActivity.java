package com.example.testapi.activities;

import android.content.Intent;
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
import com.example.testapi.models.CaptureAct;
import com.example.testapi.models.DeviceModel;
import java.util.Objects;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleActivity extends AppCompatActivity {

    ActivitySaleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imeiEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});

        binding.imeiInfoBtn.setVisibility(View.GONE);
        binding.saleInfoLayout.setVisibility(View.GONE);


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
                } else {
                    binding.imeiEdt.setError("IMEI must be exactly 15 characters long");
                    binding.barcodeBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scancode();
            }
        });

        binding.imeiInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imeiInput = binding.imeiEdt.getText().toString();
                getData(imeiInput);
            }
        });

        binding.foundToNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SaleActivity.this,SaleCutomerInfoActivity.class));
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
            binding.barcodeBtn.setVisibility(View.GONE);
        }
    });

    private void getData(String imeiInput){
        //call get device api
        Call<DeviceModel> call = ApiController
                .getInstance()
                .getapi()
                .getDevice(imeiInput);

        call.enqueue(new Callback<DeviceModel>() {
            @Override
            public void onResponse(Call<DeviceModel> call, Response<DeviceModel> response) {

                String msg = response.body().getMessage();

                if(Objects.equals(msg, "IMEI found")){

                    binding.saleInfoLayout.setVisibility(View.VISIBLE);
                    binding.scanLayout.setVisibility(View.GONE);

                    Toast.makeText(SaleActivity.this,"IMEI Found",Toast.LENGTH_SHORT).show();

                    String imei1 = response.body().getDevice_models().getImei_1();
                    String imei2 = response.body().getDevice_models().getImei_2();
                    String brand = response.body().getDevice_models().getBrand();
                    String color = response.body().getDevice_models().getColor();
                    String model = response.body().getDevice_models().getDevice();
                    String price = response.body().getDevice_models().getPrice();
                    String serial = response.body().getDevice_models().getSerialNumber();
                    binding.imeiTv1.setText(imei1 != null ? "IMEI1 : "+imei1 : "IMEI1 not available");
                    binding.imeiTv2.setText(imei2 != null ? "IMEI2 : "+imei2 : "IMEI2 not available");
                    binding.brandTv.setText(brand != null ? "BRAND : "+brand : "Brand not available");
                    binding.colorTv.setText(color != null ? "COLOR : "+color : "Color not available");
                    binding.modelTv.setText(model != null ? "MODEL : "+model : "Model not available");
                    binding.serialTv.setText(serial !=null ? "SERIAL NUMBER : "+serial : "Serial not available");
                    binding.priceTv.setText(price != null ? "HIRE SALE PRICE : "+price : "Price not available");
                }

            }

            @Override
            public void onFailure(Call<DeviceModel> call, Throwable t) {
                Toast.makeText(SaleActivity.this,"something went wrong, device not found",Toast.LENGTH_SHORT).show();
            }
        });
    }

}