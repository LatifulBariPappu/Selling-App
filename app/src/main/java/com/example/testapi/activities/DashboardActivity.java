package com.example.testapi.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.R;
import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityDashboardBinding;
import com.example.testapi.models.DashboardSaleResponse;
import com.example.testapi.models.DefaulterStatus;
import com.example.testapi.models.EmiDataResponse;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

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
                    DashboardSaleResponse.DashboardData data = response.body().getData();
                    binding.totalQuantity.setText("Total Quantity : "+data.getTotalQuantity());
                    binding.totalSale.setText("Total Sale : "+data.getTotalSale()+" BDT");
                    setupGraph(binding.chartMonth, data.getThisMonth(), "This Month");
                    setupGraph(binding.chartWeek, data.getThisWeek(), "This Week");
                    setupGraph(binding.chartToday, data.getToday(), "Today");
                }
            }

            @Override
            public void onFailure(Call<DashboardSaleResponse> call, Throwable t) {

            }
        });
    }


    private void setupGraph(GraphView graph, DashboardSaleResponse.SalesData salesData, String title) {
        // Prepare data points
        ArrayList<DataPoint> points = new ArrayList<>();
        points.add(new DataPoint(1, salesData.getTotalQuantity()));
        points.add(new DataPoint(2, salesData.getTotalSale()));

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points.toArray(new DataPoint[0]));
        series.setTitle(title);

        // Customize graph appearance
        series.setColor(R.color.my_primary);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10f);
        series.setThickness(8);

        graph.addSeries(series);

        // Customize graph axes
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(2);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(Math.max(salesData.getTotalQuantity(), salesData.getTotalSale()));

        // Set graph title
        graph.setTitle(title);
        graph.setTitleTextSize(48);
        graph.setTitleColor(R.color.my_primary);
    }
    private void getDefaulterStatus(int retailId){
        Call<DefaulterStatus> call = ApiController
                .getInstance()
                .getapi()
                .getDefaulterStatus(retailId);
        call.enqueue(new Callback<DefaulterStatus>() {
            @Override
            public void onResponse(Call<DefaulterStatus> call, Response<DefaulterStatus> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if(response.body().getStatus()==200){
                        binding.totalDefaultersTv.setText("Total Defaulters : "+response.body().getTotal_defaulters());
                        binding.totalDefaultedAmountTv.setText("Total Defaulted Amount : "+response.body().getTotal_defaulted_amount()+" BDT");
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaulterStatus> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to show data", Toast.LENGTH_SHORT).show();
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

                    binding.emiRunningTv.setText("Total Running : "+data.getEmiStatus().getEmiRunning());
                    binding.emiCompletedTv.setText("Total Completed : "+data.getEmiStatus().getEmiCompleted());

                    binding.totalCollectionsTv.setText("Total Collections : "+data.getCollections().getTotalCollection()+" BDT");
                    binding.totalDueTv.setText("Total Due : "+data.getCollections().getTotalDue()+" BDT");

                    binding.totalUnlockedTv.setText("Total Unlocked : "+data.getLocked().getTotalUnlocked());
                    binding.totalLockedTv.setText("Total Locked : "+data.getLocked().getTotalLocked());
                }
            }

            @Override
            public void onFailure(Call<EmiDataResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to show data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}