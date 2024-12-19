package com.example.testapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.testapi.R;
import com.example.testapi.databinding.ActivityDistributorHomeBinding;

public class DistributorHomeActivity extends AppCompatActivity {
    ActivityDistributorHomeBinding binding;
    TextView drawerIdTv, drawerNameTv,drawerEmailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDistributorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkexistence();

        drawerIdTv = findViewById(R.id.distributorDrawerIdTv);
        drawerNameTv = findViewById(R.id.distributorDrawerNameTv);
        drawerEmailTv = findViewById(R.id.distributorDrawerEmailTv);

        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);

        int distributorId = sp.getInt("distributorId",0);
        drawerIdTv.setText(String.valueOf(distributorId));

        String distributorName = sp.getString("distributorName","");
        drawerNameTv.setText(distributorName);


        String distributorEmail = sp.getString("distributorEmail","");
        drawerEmailTv.setText(distributorEmail);

        findViewById(R.id.distributorDrawerMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(binding.distributorDrawerLayout);
            }
        });
        findViewById(R.id.distributorNavHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        findViewById(R.id.distributorNavLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
                sp.edit().remove("logged").apply();
                startActivity(new Intent(getApplicationContext(), FirstActivity.class));
                finish();
            }
        });

    }
    private void checkexistence() {
        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
        String userCategory = sp.getString("logged","");
        String distributorName = sp.getString("distributorName","");
        if(userCategory.equals("Distributor")){
            binding.textView.setText("Welcome " + distributorName);
        }
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
        closeDrawer(binding.distributorDrawerLayout);
    }
}