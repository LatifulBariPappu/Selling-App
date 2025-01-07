package com.example.testapi.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityBalanceBinding;
import com.example.testapi.models.BalanceModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
        int retailerId = sp.getInt("retailerId",0);
        String retailerName =sp.getString("retailerName","");
        setBalance(retailerId);

        binding.retailerNameTv.setText(retailerName);
        binding.retailerIdTv.setText(String.valueOf(retailerId));

    }
    private void setBalance(int retailerId) {
        Call<BalanceModel> call = ApiController
                .getInstance()
                .getapi()
                .getBalance(retailerId);

        call.enqueue(new Callback<BalanceModel>() {
            @Override
            public void onResponse(Call<BalanceModel> call, Response<BalanceModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BalanceModel balanceModel = response.body();

                    // Check if the data field is not null before accessing it
                    if (balanceModel.getData() != null) {
                        BalanceModel.BalanceData data = balanceModel.getData();

                        // Set values in the UI from BalanceData
                        int purchasedSubscription = data.getPurchasedSubscription();
                        int consumedSubscription = data.getConsumedSubscription();
                        int balance = data.getBalance();

                        binding.totalPurchasedTv.setText(String.valueOf(purchasedSubscription));
                        binding.consumedPurchasedTv.setText(String.valueOf(consumedSubscription));
                        binding.balanceSubscriptionTv.setText(String.valueOf(balance));
                        binding.mainBalancetv.setText(String.valueOf(balance));
                    } else {
                        Toast.makeText(getApplicationContext(), "No balance data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle different response codes
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BalanceModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}