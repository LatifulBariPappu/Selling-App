package com.example.testapi.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityPaymentBinding;
import com.example.testapi.models.PaymentModel;
import com.example.testapi.models.PaymentRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    ActivityPaymentBinding binding;
    String imei;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setOnClickListener(v -> finish());

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        binding.paymentDateTv.setText(currentDate);

        SharedPreferences sp = getSharedPreferences("defaulters", Context.MODE_PRIVATE);
        String isDefaulter = sp.getString("isDefaulter","");
        if("yes".equals(isDefaulter)){
            String defaulterImei1 = sp.getString("defaulterImei1","") ;
            imei = defaulterImei1;
            binding.paymentImeiTv.setText("Make payment for IMEI1 ( "+defaulterImei1+" )");
        }else if("no".equals(isDefaulter)){
            String imei1 = sp.getString("imei1","");
            imei = imei1;
            binding.paymentImeiTv.setText("Make payment for IMEI1 ( "+imei1+" )");
        }
        binding.paymentDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Show DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(PaymentActivity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            // Update the TextView with the selected date
                            String selectedDate = selectedYear + "-" + String.format("%02d", (selectedMonth + 1)) + "-" + String.format("%02d", selectedDay);
                            binding.paymentDateTv.setText(selectedDate);
                        }, year, month, day);

                // Set the maximum date to today
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        binding.confirmPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentAmountInput = binding.paymentAmountEdt.getText().toString().trim();
                // Validate if the payment amount field is empty
                if (paymentAmountInput.isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Please enter a payment amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                int paymentAmount;
                try {
                    // Parse the input to an integer
                    paymentAmount = Integer.parseInt(paymentAmountInput);

                    // Check if payment amount is valid (e.g., not zero or negative)
                    if (paymentAmount <= 0) {
                        Toast.makeText(PaymentActivity.this, "Payment amount must be greater than 0", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(PaymentActivity.this, "Invalid payment amount. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Validate the payment date
                String paymentDate = binding.paymentDateTv.getText().toString();
                if (paymentDate.isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Please select a payment date", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Parse the selected payment date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date selectedDate = sdf.parse(paymentDate);
                    Date currentDate = new Date();

                    // Check if the selected date is after the current date
                    if (selectedDate != null && selectedDate.after(currentDate)) {
                        Toast.makeText(PaymentActivity.this, "Payment date cannot be in the future", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (Exception e) {
                    Toast.makeText(PaymentActivity.this, "Invalid payment date format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create the payment request
                PaymentRequest request = new PaymentRequest(imei,paymentAmount,paymentDate);
                String authorization = "iF3PTw5zRS7JdKeu2ULE3A==";

                // Call the payment API
                paymentApi(authorization,request);

            }
        });


    }
    private void paymentApi(String authorization, PaymentRequest request){
        binding.confirmPaymentBtn.setVisibility(View.GONE);
        binding.confirmPaymentProgress.setVisibility(View.VISIBLE);
        Call<PaymentModel> call = ApiController
                .getInstance()
                .getapi()
                .getPayment(authorization,request);
        call.enqueue(new Callback<PaymentModel>() {
            @Override
            public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
                binding.confirmPaymentBtn.setVisibility(View.VISIBLE);
                binding.confirmPaymentProgress.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body()!=null){
                    String msg = response.body().getData().getMessage();
                    if("Payment Successful".equals(msg)){
                        Toast.makeText(getApplicationContext(),"Payment Successful",Toast.LENGTH_SHORT).show();
                        finish();
                    } else if ("Device Dose not Exist".equals(msg)) {
                        Toast.makeText(getApplicationContext(), "Device Dose not Exist", Toast.LENGTH_SHORT).show();
                    } else if ("Payment Unsuccessful".equals(msg)) {
                        Toast.makeText(getApplicationContext(), "Payment amount and date is required", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"response body is null",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentModel> call, Throwable t) {
                binding.confirmPaymentBtn.setVisibility(View.VISIBLE);
                binding.confirmPaymentProgress.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Failed : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}