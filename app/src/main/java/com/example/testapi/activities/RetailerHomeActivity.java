package com.example.testapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
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
        idTv.setText("ID : "+String.valueOf(retailerId));
        nameTv.setText("Welcome "+retailerName);

        findViewById(R.id.drawerMenu).setOnClickListener(new View.OnClickListener() {
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
        binding.deviceListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DeviceListsActivity.class));
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