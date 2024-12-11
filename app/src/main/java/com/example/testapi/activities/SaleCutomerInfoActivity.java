package com.example.testapi.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.api.MyApi;
import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivitySaleCutomerInfoBinding;
import com.example.testapi.models.SaleRequestModel;
import com.example.testapi.models.SaleResponseModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SaleCutomerInfoActivity extends AppCompatActivity {

    ActivitySaleCutomerInfoBinding binding;

    LocalDate date;
    DateTimeFormatter formatter;
    String formattedDate = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaleCutomerInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences= getSharedPreferences("device_details",MODE_PRIVATE);
        int price = sharedPreferences.getInt("price",0);
        binding.hireSalePrice.setText(String.valueOf(price));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDate = date.format(formatter);
        }

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
        binding.customerNameEdt.addTextChangedListener(textWatcher);
        binding.customerAddressEdt.addTextChangedListener(textWatcher);
        binding.customerMobileEdt.addTextChangedListener(textWatcher);
        binding.customerNidEdt.addTextChangedListener(textWatcher);
        binding.retailName.addTextChangedListener(textWatcher);
        binding.retailId.addTextChangedListener(textWatcher);
        binding.downPayment.addTextChangedListener(textWatcher);
        binding.paymentDate.addTextChangedListener(textWatcher);


        binding.customerMobileEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) { // If the EditText loses focus
                    String input = binding.customerMobileEdt.getText().toString().trim();
                    if (input.isEmpty()) {
                        // Set error if input is empty
                        binding.customerMobileEdt.setError("Mobile field cannot be empty");
                    } else if (!input.matches("^(\\+880|880|01)[1-9]\\d{8}$")) {
                        // Set error if input contains alphabetic characters
                        binding.customerMobileEdt.setError("Please enter actual mobile number");
                    } else {
                        // Clear the error if the input is valid
                        binding.customerMobileEdt.setError(null);
                    }
                }
            }
        });
        binding.customerNidEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) { // If the EditText loses focus
                    String input = binding.customerNidEdt.getText().toString().trim();
                    if (input.isEmpty()) {
                        // Set error if input is empty
                        binding.customerNidEdt.setError("NID field cannot be empty");
                    } else if (!input.matches("^(\\d{10}|\\d{13}|\\d{17})$")) {
                        // Set error if input contains alphabetic characters
                        binding.customerNidEdt.setError("Please enter actual NID");
                    } else {
                        // Clear the error if the input is valid
                        binding.customerNidEdt.setError(null);
                    }
                }
            }
        });



        assert formattedDate != null;
        binding.paymentDate.setText(formattedDate);





        binding.confirmSaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random= new Random();
                String customerId = String.valueOf(1_000_000_000L + (long) (random.nextDouble() * 9_000_000_000L));
                String customerName = binding.customerNameEdt.getText().toString();
                String customerAddress = binding.customerAddressEdt.getText().toString();
                int customerNID = Integer.parseInt(binding.customerNidEdt.getText().toString());
                String customerMobile = binding.customerMobileEdt.getText().toString();
                String plazaName = binding.retailName.getText().toString();
                String plazaId = binding.retailId.getText().toString();
                int numberOfInstallment = Integer.parseInt(binding.numberofInstallation.getText().toString());
                int downPayment = Integer.parseInt(binding.downPayment.getText().toString());

                int randomNumber = 1000 + random.nextInt(9000);
                String posInvoiceNumber = formattedDate+"IISL"+randomNumber;
                String downPaymentDate = binding.paymentDate.getText().toString();

                SharedPreferences sp = getSharedPreferences("device_details",MODE_PRIVATE);
                String imei1 = sp.getString("imei1","");
                String barcode = sp.getString("barcode","");
                String brand = sp.getString("brand","");
                String model = sp.getString("model","");
                String color = sp.getString("color","");
//                int hireSalePrice = sp.getInt("price",0);
                int hireSalePrice = Integer.parseInt(binding.hireSalePrice.getText().toString().trim());
                String salesBy = sp.getString("distributor","");

                SaleRequestModel request = new SaleRequestModel(customerId,customerName,customerAddress,customerNID,customerMobile,salesBy,plazaName,plazaId,posInvoiceNumber,salesBy,imei1,barcode,brand,model,color,hireSalePrice,numberOfInstallment,downPayment,downPaymentDate);

                String auth = "iF3PTw5zRS7JdKeu2ULE3A==";

                getInvoiceSuccess(auth,request);

            }
        });

    }

    private void checkFields() {
        String text1=binding.customerNameEdt.getText().toString();
        String text2=binding.customerAddressEdt.getText().toString();
        String text3=binding.customerMobileEdt.getText().toString();
        String text4=binding.customerNidEdt.getText().toString();
        String text5=binding.retailId.getText().toString();
        String text6=binding.retailName.getText().toString();
        String text8=binding.paymentDate.getText().toString();
        String text9=binding.downPayment.getText().toString();
        String text10=binding.numberofInstallation.getText().toString();
        if(!text1.isEmpty() && !text2.isEmpty() && !text3.isEmpty() && !text4.isEmpty() && !text5.isEmpty() && !text6.isEmpty() && !text8.isEmpty() && !text9.isEmpty() && !text10.isEmpty()){
            binding.confirmSaleBtn.setVisibility(View.VISIBLE);
        }else{
            binding.confirmSaleBtn.setVisibility(View.GONE);
        }
    }

    private void getInvoiceSuccess(String auth, SaleRequestModel req){
        Call<SaleResponseModel> call = ApiController
                .getInstance()
                .getapi()
                .sendRequest(auth,req);
        call.enqueue(new Callback<SaleResponseModel>() {
            @Override
            public void onResponse(Call<SaleResponseModel> call, Response<SaleResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SaleResponseModel responseData = response.body();
                    if ("Action Successful".equals(responseData.getDataObject().getMessage())) {
                        Toast.makeText(SaleCutomerInfoActivity.this, "Action Successful", Toast.LENGTH_SHORT).show();
                    } else if ("Action Unsuccessful".equals(responseData.getDataObject().getMessage())) {
                        Toast.makeText(SaleCutomerInfoActivity.this, "Action Unsuccessful", Toast.LENGTH_SHORT).show();
                    }else if ("Device Already Exist".equals(responseData.getDataObject().getMessage())) {
                        Toast.makeText(SaleCutomerInfoActivity.this, "Device Already Exist", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SaleCutomerInfoActivity.this, "Action Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SaleCutomerInfoActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<SaleResponseModel> call, Throwable t) {
                Toast.makeText(SaleCutomerInfoActivity.this,  "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}