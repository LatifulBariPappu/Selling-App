package com.example.testapi.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testapi.R;
import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityDashboardBinding;
import com.example.testapi.models.DashboardSaleResponse;
import com.example.testapi.models.DefaulterStatus;
import com.example.testapi.models.EmiDataResponse;
import com.example.testapi.models.TodayDefaulterAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.todayDefaultersRv.setLayoutManager(new LinearLayoutManager(this));
        binding.backDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
        int retailerId = sp.getInt("retailerId",0);
        getDashboardSale(retailerId);
        getDefaulterStatus(retailerId);
        getEmistatus(retailerId);
    }

    private void getDashboardSale(int retailerId) {
        Call<DashboardSaleResponse> call = ApiController
                .getInstance()
                .getapi()
                .getDashboardData(retailerId);

        call.enqueue(new Callback<DashboardSaleResponse>() {
            @Override
            public void onResponse(Call<DashboardSaleResponse> call, Response<DashboardSaleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DashboardSaleResponse data = response.body();
                    int status = data.getStatus();
                    if(status == 200){
                        int todayQuantity = data.getData().getToday().getTotal_quantity();
                        int todaySale = data.getData().getToday().getTotal_sale();
                        int thisWeekQuantity = data.getData().getThis_week().getTotal_quantity();
                        int thisWeekSale = data.getData().getThis_week().getTotal_sale();
                        int thisMonthQuantity = data.getData().getThis_month().getTotal_quantity();
                        int thisMonthSale = data.getData().getThis_month().getTotal_sale();
                        int totalQuantity = data.getData().getTotal().getTotal_quantity();
                        int totalSale = data.getData().getTotal().getTotal_sale();
                        binding.todayQuantity.setText(String.valueOf(todayQuantity));
                        binding.todaySaleAmount.setText(todaySale+" BDT");
                        binding.weekQuantity.setText(String.valueOf(thisWeekQuantity));
                        binding.weekSaleAmount.setText(thisWeekSale+" BDT");
                        binding.monthQuantity.setText(String.valueOf(thisMonthQuantity));
                        binding.monthSaleAmount.setText(thisMonthSale+" BDT");

                        binding.totalQuantity.setText(String.valueOf(totalQuantity));
                        binding.totalSaleAmount.setText(totalSale+" BDT");
                    }

                }
            }
            @Override
            public void onFailure(Call<DashboardSaleResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to create line graph", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDefaulterStatus(int retailId){
        Call<DefaulterStatus> call = ApiController
                .getInstance()
                .getapi()
                .getDefaulterStatus(retailId);
        call.enqueue(new Callback<DefaulterStatus>() {
            @Override
            public void onResponse(Call<DefaulterStatus> call, Response<DefaulterStatus> response) {
                if (response.isSuccessful()  && response.body() != null) {
                    DefaulterStatus obj = response.body();
                    if(obj.getStatus()==200){
                        binding.todayDefaultedAmountTv.setText("Amount : "+obj.getTotal_defaulted_amount());
                        binding.todayDefaultersTv.setText("Defaulters : "+obj.getTotal_defaulters());

                        List<DefaulterStatus.TodayDefaulters> todayDefaultersList = obj.getTodays_defaulters();

                        binding.todayDefaulterHeadingTv.setText("Today's defaulters listed below");
                        binding.todayDefaulterHeadingTv.setTextColor(getResources().getColor(R.color.custom_red));

                        TodayDefaulterAdapter adapter = new TodayDefaulterAdapter(todayDefaultersList);
                        binding.todayDefaultersRv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaulterStatus> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getEmistatus(int retailId){
        Call<EmiDataResponse> call = ApiController
                .getInstance()
                .getapi()
                .getEmiStatus(retailId);
        call.enqueue(new Callback<EmiDataResponse>() {
            @Override
            public void onResponse(Call<EmiDataResponse> call, Response<EmiDataResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmiDataResponse emiDataResponse = response.body();
                    EmiDataResponse.EmiData data = emiDataResponse.getData();

                    binding.emiRunningTv.setText("Running : "+data.getEmiStatus().getEmiRunning());
                    binding.emiCompletedTv.setText("Completed : "+data.getEmiStatus().getEmiCompleted());

                    binding.totalCollectionsTv.setText("Collections : "+data.getCollections().getTotalCollection());
                    binding.totalDueTv.setText("Due : "+data.getCollections().getTotalDue());

                    binding.totalUnlockedTv.setText("Unlocked : "+data.getLocked().getTotalUnlocked());
                    binding.totalLockedTv.setText("Locked : "+data.getLocked().getTotalLocked());
                }
            }

            @Override
            public void onFailure(Call<EmiDataResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}