package com.example.testapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        binding.edt1.addTextChangedListener(textWatcher);
        binding.edt2.addTextChangedListener(textWatcher);

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=binding.edt1.getText().toString();
                String password=binding.edt2.getText().toString();
                processlogin(username, password);
            }
        });
    }
    private void checkFields() {
        String text1=binding.edt1.getText().toString().trim();
        String text2=binding.edt2.getText().toString().trim();
        if(!text1.isEmpty() && !text2.isEmpty()){
            binding.loginbtn.setVisibility(View.VISIBLE);
        }else{
            binding.loginbtn.setVisibility(View.GONE);
        }
    }

    private void processlogin(String username, String password) {

        binding.loginbtn.setVisibility(View.GONE);
        binding.loginProgress.setVisibility(View.VISIBLE);

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
                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);
                    if(response.isSuccessful() && response.body()!=null){
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
                    }else {
                        Toast.makeText(getApplicationContext(), "enter correct credentials", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<model> call, @NonNull Throwable t) {
                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Failed to call API",Toast.LENGTH_SHORT).show();
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

                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);

                    if(response.isSuccessful() && response.body()!=null){
                        String msg=response.body().getMessage();
                        if("Login successful".equals(msg)){
                            SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("logged","Retailer");
                            editor.apply();
                            Toast.makeText(getApplicationContext(),"retailer login success",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,RetailerHomeActivity.class));
                            finish();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "check credentials", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<RetailerModel> call, @NonNull Throwable t) {
                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Failed to call API",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}