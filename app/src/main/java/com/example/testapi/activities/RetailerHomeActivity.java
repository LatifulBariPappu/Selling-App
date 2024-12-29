package com.example.testapi.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.testapi.R;
import com.example.testapi.databinding.ActivityRetailerHomeBinding;

public class RetailerHomeActivity extends AppCompatActivity {
    ActivityRetailerHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetailerHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
        int retailerId = sp.getInt("retailerId",0);
        String retailerName =sp.getString("retailerName","");

        TextView idTv = findViewById(R.id.drawerIdTv);
        TextView nameTv= findViewById(R.id.drawerNameTv);
        idTv.setText("ID : "+retailerId);
        nameTv.setText("Welcome "+retailerName);

        findViewById(R.id.drawerMenu).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                openDrawer(binding.drawerLayout);
            }
        });
        findViewById(R.id.navLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
                sp.edit().remove("logged").apply();
                startActivity(new Intent(getApplicationContext(), FirstActivity.class));
                finish();
            }
        });
        findViewById(R.id.navHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        findViewById(R.id.navNewSale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewSaleActivity.class));
            }
        });
        findViewById(R.id.navProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RetailerProfileActivity.class));
            }
        });
        findViewById(R.id.navDeviceList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DeviceListsActivity.class));
            }
        });

        binding.newSaleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewSaleActivity.class));
            }
        });
        binding.profileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RetailerProfileActivity.class));
            }
        });
        binding.deviceListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DeviceListsActivity.class));
            }
        });
        findViewById(R.id.navCustomers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomersListActivity.class));
            }
        });
        findViewById(R.id.navBalance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BalanceActivity.class));
            }
        });
        findViewById(R.id.navCreditScore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreditScoreActivity.class));
            }
        });
        findViewById(R.id.navDashboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            }
        });
        findViewById(R.id.navReport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ReportActivity.class));
            }
        });
        
        binding.customerListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomersListActivity.class));
            }
        });
        binding.balanceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BalanceActivity.class));
            }
        });
        binding.creditScoreCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreditScoreActivity.class));
            }
        });
        binding.dashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            }
        });
        binding.reportCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ReportActivity.class));
            }
        });
        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Optional: Handle any animation or effects during the slide
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Disable interaction with the main layout when the drawer is open
                binding.retailerHomeGridLayout.setClickable(false);
                binding.newSaleCardView.setClickable(false);
                binding.deviceListCardView.setClickable(false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // Enable interaction with the main layout when the drawer is closed
                binding.retailerHomeGridLayout.setClickable(true);
                binding.newSaleCardView.setClickable(true);
                binding.deviceListCardView.setClickable(true);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Optional: Handle any drawer state changes
            }
        });
    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(binding.drawerLayout);
    }
}