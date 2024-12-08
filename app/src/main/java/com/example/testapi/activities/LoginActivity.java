package com.example.testapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityLoginBinding;
import com.example.testapi.models.RetailerModel;
import com.example.testapi.models.model;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=binding.edt1.getText().toString();
                String password=binding.edt2.getText().toString();
                processlogin(username, password);
            }
        });
    }

    private void processlogin(String username, String password) {

        SharedPreferences sp = getSharedPreferences("login_as",MODE_PRIVATE);
        String userCategory = sp.getString("user","");

        if(userCategory.equals("Distributor")){

            //call distributor login api
            Call<model> call=ApiController
                    .getInstance()
                    .getapi().distributorLogin(username, password);

            call.enqueue(new Callback<model>() {
                @Override
                public void onResponse(@NonNull Call<model> call, @NonNull Response<model> response) {
                    assert response.body() != null;
                    String msg=response.body().getMessage();
                    if(Objects.equals(msg, "Login successful")){
                        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("logged","Distributor");
                        editor.apply();
                        Toast.makeText(getApplicationContext(),"distributor login success",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, Dashboard.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<model> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(),"wrong credentials, try again",Toast.LENGTH_SHORT).show();
                }
            });
        }


        if(userCategory.equals("Retailer")){

            //call Retailer login api
            Call<RetailerModel> call = ApiController
                    .getInstance()
                    .getapi()
                    .retailerLogin(username,password);

            call.enqueue(new Callback<RetailerModel>() {
                @Override
                public void onResponse(@NonNull Call<RetailerModel> call, @NonNull Response<RetailerModel> response) {
                    assert response.body() != null;
                    String msg=response.body().getMessage();
                    if(Objects.equals(msg, "Login successful")){
                        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("logged","Retailer");
                        editor.apply();
                        Toast.makeText(getApplicationContext(),"retailer login success",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,RetailerHomeActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RetailerModel> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(),"wrong credentials, try again",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}