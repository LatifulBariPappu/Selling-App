package com.example.testapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityRetailerHomeBinding;
import com.example.testapi.databinding.SalePopupWindowBinding;
import com.example.testapi.models.DeviceModel;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailerHomeActivity extends AppCompatActivity {

    ActivityRetailerHomeBinding binding;
    SalePopupWindowBinding popupWindowBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetailerHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        popupWindowBinding=SalePopupWindowBinding.inflate(getLayoutInflater());
        popupWindowBinding.imeiEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});

        binding.retailerLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
                sp.edit().remove("logged").apply();
                startActivity(new Intent(getApplicationContext(), FirstActivity.class));
                finish();
            }
        });

        binding.salebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // Create the PopupWindow
                    PopupWindow popupWindow = new PopupWindow(
                            popupWindowBinding.getRoot(),
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            true
                    );

                    // Show the PopupWindow
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    popupWindowBinding.imeiNextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String imeiInput = popupWindowBinding.imeiEdt.getText().toString();

                            popupWindowBinding.imeiEdt.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (s.length() == 15) {
                                        popupWindowBinding.imeiEdt.setError(null);
                                    } else {
                                        popupWindowBinding.imeiEdt.setError("IMEI must be exactly 15 characters long");
                                    }
                                }
                            });

                            //call get device api
                            Call<DeviceModel> call = ApiController
                                    .getInstance()
                                    .getapi()
                                    .getDevice(imeiInput);

                            call.enqueue(new Callback<DeviceModel>() {
                                @Override
                                public void onResponse(Call<DeviceModel> call, Response<DeviceModel> response) {
                                    assert response.body() != null;
                                    String msg = response.body().getMessage();

                                    if(Objects.equals(msg, "IMEI found")){
                                        Toast.makeText(getApplicationContext(),"IMEI Found",Toast.LENGTH_SHORT).show();
                                        popupWindow.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DeviceModel> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Device not found",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });

            }
        });

    }

}