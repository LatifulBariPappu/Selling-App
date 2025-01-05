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

import java.util.List;
import java.util.Map;

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
                    // Reset UI elements
                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);

                    if (response.isSuccessful() && response.body() != null) {
                        DistributorModel distributorModel = response.body();
                        String msg = distributorModel.getMessage();

                        if ("Login successful".equals(msg)) {
                            // Save distributor details in SharedPreferences
                            SharedPreferences sp = getSharedPreferences("saved_login", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            DistributorModel.Distributor distributor = distributorModel.getDistributorObject();

                            if (distributor != null) {
                                editor.putString("logged", "Distributor");
                                editor.putInt("distributorId", distributor.getId());
                                editor.putString("distributorName", distributor.getName());
                                editor.putString("distributorMobile", distributor.getMobile());
                                editor.putString("distributorEmail", distributor.getEmail());
                                editor.putString("distributorAddress", distributor.getAddress());
                                editor.putInt("noOfSubscription", distributor.getNo_of_subscription());
                            }
                            editor.putInt("distributorStatus", distributorModel.getStatus());
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Welcome " + distributor.getName(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DistributorHomeActivity.class));
                            finish();
                        } else if ("Validation errors".equals(msg)) {
                            // Handle validation errors dynamically
                            Map<String, List<String>> errors = distributorModel.getErrors();
                            if (errors != null) {
                                StringBuilder errorMessage = new StringBuilder();
                                for (Map.Entry<String, List<String>> entry : errors.entrySet()) {
                                    for (String error : entry.getValue()) {
                                        errorMessage.append(error).append("\n");
                                    }
                                }
                                Toast.makeText(getApplicationContext(), errorMessage.toString().trim(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Validation errors occurred.", Toast.LENGTH_SHORT).show();
                            }
                        } else if ("Incorrect password".equals(msg)) {
                            Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                        } else if ("Mobile number not found".equals(msg)) {
                            Toast.makeText(getApplicationContext(), "Mobile number not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle unsuccessful responses (e.g., server errors)
                        Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DistributorModel> call, @NonNull Throwable t) {
                    // Reset UI elements and display failure message
                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    // Reset UI elements
                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);

                    if (response.isSuccessful() && response.body() != null) {
                        RetailerModel retailerModel = response.body();
                        String msg = retailerModel.getMessage();

                        if ("Login successful".equals(msg)) {
                            // Save retailer details in SharedPreferences
                            SharedPreferences sp = getSharedPreferences("saved_login", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            RetailerModel.Retail retail = retailerModel.getRetailObject();

                            if (retail != null) {
                                editor.putString("logged", "Retailer");
                                editor.putInt("retailerId", retail.getId());
                                editor.putString("retailerName", retail.getName());
                                editor.putString("retailerMobile", retail.getMobile());
                                editor.putString("retailerAddress", retail.getAddress());
                                editor.putString("retailDistributorName", retail.getDistributor_name());
                            }
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Welcome " + (retail != null ? retail.getName() : ""), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, RetailerHomeActivity.class));
                            finish();
                        } else if ("Validation errors".equals(msg)) {
                            // Handle validation errors dynamically
                            Map<String, List<String>> errors = retailerModel.getErrors();
                            if (errors != null) {
                                StringBuilder errorMessage = new StringBuilder();
                                for (Map.Entry<String, List<String>> entry : errors.entrySet()) {
                                    for (String error : entry.getValue()) {
                                        errorMessage.append(error).append("\n");
                                    }
                                }
                                Toast.makeText(getApplicationContext(), errorMessage.toString().trim(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Validation errors occurred.", Toast.LENGTH_SHORT).show();
                            }
                        } else if ("Incorrect password".equals(msg)) {
                            Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                        } else if ("Mobile number not found".equals(msg)) {
                            Toast.makeText(getApplicationContext(), "Mobile number not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle unsuccessful responses (e.g., server errors)
                        Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RetailerModel> call, @NonNull Throwable t) {
                    // Reset UI elements and display failure message
                    binding.loginbtn.setVisibility(View.VISIBLE);
                    binding.loginProgress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}