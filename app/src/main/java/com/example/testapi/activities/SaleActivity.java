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
import androidx.annotation.NonNull;
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
                String imeiInput = Objects.requireNonNull(binding.imeiEdt.getText()).toString();
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
        binding.imeiProgress.setVisibility(View.VISIBLE);
        binding.imeiInfoBtn.setVisibility(View.GONE);
        //call get device api
        Call<DeviceModel> call = ApiController
                .getInstance()
                .getapi()
                .getDevice(imeiInput);

        call.enqueue(new Callback<DeviceModel>() {
            @Override
            public void onResponse(Call<DeviceModel> call, Response<DeviceModel> response) {
                binding.imeiProgress.setVisibility(View.GONE);
                binding.imeiInfoBtn.setVisibility(View.VISIBLE);
                if(response.isSuccessful() && response.body()!=null){
                    String msg = response.body().getMessage();
                    if("IMEI found".equals(msg)){
                        binding.saleInfoLayout.setVisibility(View.VISIBLE);
                        binding.scanLayout.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"IMEI Found",Toast.LENGTH_SHORT).show();

                        SharedPreferences sp = getSharedPreferences("device_details",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();

                        String imei1 = response.body().getDevice_models().getImei_1();
                        editor.putString("imei1",imei1);
                        editor.apply();
                        String imei2 = response.body().getDevice_models().getImei_2();
                        editor.putString("barcode",imei2);
                        editor.apply();
                        String brand = response.body().getDevice_models().getBrand();
                        editor.putString("brand",brand);
                        editor.apply();
                        String color = response.body().getDevice_models().getColor();
                        editor.putString("color",color);
                        editor.apply();
                        String model = response.body().getDevice_models().getDevice();
                        editor.putString("model",model);
                        editor.apply();
                        int price =response.body().getDevice_models().getHire_sale_price();
                        editor.putInt("price",price);
                        editor.apply();
                        String serial = response.body().getDevice_models().getSerial_number();
                        editor.putString("serial",serial);
                        editor.apply();
                        String distributorName = response.body().getDevice_models().getDistributor_name();
                        editor.putString("distributor",distributorName);
                        editor.apply();
                        binding.imeiTv1.setText(imei1 != null ? "IMEI1 : "+imei1 : "IMEI1 not available");
                        binding.imeiTv2.setText(imei2 != null ? "IMEI2 : "+imei2 : "IMEI2 not available");
                        binding.brandTv.setText(brand != null ? "BRAND : "+brand : "Brand not available");
                        binding.colorTv.setText(color != null ? "COLOR : "+color : "Color not available");
                        binding.modelTv.setText(model != null ? "MODEL : "+model : "Model not available");
                        binding.serialTv.setText(serial !=null ? "SERIAL NUMBER : "+serial : "Serial not available");
                        binding.distributorTv.setText(distributorName != null ? "DISTRIBUTOR NAME : "+distributorName : "DistributorName not available");
                        binding.priceTv.setText("HIRE SALE PRICE : "+price);
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "device not found", Toast.LENGTH_SHORT).show();
                    binding.imeiEdt.setText("");
                }
            }
            @Override
            public void onFailure(@NonNull Call<DeviceModel> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"API Call Failed: " + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}