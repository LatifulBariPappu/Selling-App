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
import com.example.testapi.models.DistributorModel;
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
                process_login(username, password);
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

    private void process_login(String username, String password) {

        binding.loginbtn.setVisibility(View.GONE);
        binding.loginProgress.setVisibility(View.VISIBLE);

        SharedPreferences sp = getSharedPreferences("login_as",MODE_PRIVATE);
        String userCategory = sp.getString("user","");

        if(userCategory.equals("Distributor")){

            //call distributor login api
            Call<DistributorModel> call=ApiController
                    .getInstance()
                    .getapi().distributorLogin(username, password);

            call.enqueue(new Callback<DistributorModel>() {
                @Override
                public void onResponse(@NonNull Call<DistributorModel> call, @NonNull Response<DistributorModel> response) {
                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);
                    if(response.isSuccessful() && response.body()!=null){
                        String msg=response.body().getMessage();
                        if("Login successful".equals(msg)){
                            SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("logged","Distributor");
                            int distributorId = response.body().getDistributorObject().getId();
                            editor.putInt("distributorId",distributorId);
                            String distributorName = response.body().getDistributorObject().getName();
                            editor.putString("distributorName",distributorName);
                            String distributorMobile = response.body().getDistributorObject().getMobile();
                            editor.putString("distributorMobile",distributorMobile);
                            String distributorEmail = response.body().getDistributorObject().getEmail();
                            editor.putString("distributorEmail",distributorEmail);
                            String distributorAddress = response.body().getDistributorObject().getAddress();
                            editor.putString("distributorAddress",distributorAddress);
                            int noOfSubscription = response.body().getDistributorObject().getNo_of_subscription();
                            editor.putInt("noOfSubscription",noOfSubscription);
                            int distributorStatus = response.body().getStatus();
                            editor.putInt("distributorStatus",distributorStatus);
                            editor.apply();
                            Toast.makeText(getApplicationContext(),"Welcome "+distributorName,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DistributorHomeActivity.class));
                            finish();
                        } else if ("Validation errors".equals(msg)){
                            Toast.makeText(getApplicationContext(), "The mobile number must be exactly 11 digits Or The password field is required.", Toast.LENGTH_SHORT).show();
                        } else if ("Incorrect password".equals(msg)) {
                            Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                        } else if ("Mobile number not found".equals(msg)) {
                            Toast.makeText(getApplicationContext(), "Mobile number not found", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "response body is null", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<DistributorModel> call, @NonNull Throwable t) {
                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Failed : "+t.getMessage(),Toast.LENGTH_SHORT).show();
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
                            int retailerId= response.body().getRetailObject().getId();
                            editor.putInt("retailerId",retailerId);
                            String retailerName=response.body().getRetailObject().getName();
                            editor.putString("retailerName",retailerName);
                            String retailerMobile = response.body().getRetailObject().getMobile();
                            editor.putString("retailerMobile",retailerMobile);
                            String retailerAddress = response.body().getRetailObject().getAddress();
                            editor.putString("retailerAddress",retailerAddress);
                            editor.apply();
                            Toast.makeText(getApplicationContext(),"Welcome "+retailerName,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,RetailerHomeActivity.class));
                            finish();
                        }else if ("Validation errors".equals(msg)){
                            Toast.makeText(getApplicationContext(), "The mobile number must be exactly 11 digits Or The password field is required.", Toast.LENGTH_SHORT).show();
                        } else if ("Incorrect password".equals(msg)) {
                            Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                        } else if ("Mobile number not found".equals(msg)) {
                            Toast.makeText(getApplicationContext(), "Mobile number not found", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "response body is null", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<RetailerModel> call, @NonNull Throwable t) {
                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Failed : "+t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}