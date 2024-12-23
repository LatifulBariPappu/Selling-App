package com.example.testapi.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivitySaleCutomerInfoBinding;
import com.example.testapi.models.SaleRequestModel;
import com.example.testapi.models.SaleResponseModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SaleCustomerInfoActivity extends AppCompatActivity {

    ActivitySaleCutomerInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaleCutomerInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences= getSharedPreferences("device_details",MODE_PRIVATE);
        int price = sharedPreferences.getInt("price",0);
        binding.hireSalePrice.setText(String.valueOf(price));

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            date = LocalDate.now();
//            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//            formattedDate = date.format(formatter);
//        }
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        binding.selectDate.setText(currentDate);
        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        binding.downPayment.addTextChangedListener(textWatcher);
        binding.hireSalePrice.addTextChangedListener(textWatcher);
        binding.numberofInstallation.addTextChangedListener(textWatcher);

        binding.selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Show DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(SaleCustomerInfoActivity.this,
                        (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                            // Update the TextView with the selected date
                            String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" +selectedDay ;
                            binding.selectDate.setText(selectedDate);
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        binding.customerMobileEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        String input = binding.customerMobileEdt.getText().toString().trim();
                        if (input.isEmpty()) {
                            binding.customerMobileEdt.setError("Mobile field cannot be empty");
                        } else if (!input.matches("^(\\+880|880|01)[1-9]\\d{8}$")) {
                            binding.customerMobileEdt.setError("Please enter a valid mobile number");
                        } else {
                            binding.customerMobileEdt.setError(null);
                        }
                    } catch (NumberFormatException e) {
                        // Handle number input exception gracefully
                        e.printStackTrace();
                        binding.customerMobileEdt.setError("Invalid number format");
                    } catch (Exception e) {
                        // Catch any other exceptions to prevent crashing
                        e.printStackTrace();
                    }
                }
            }
        });
        binding.numberofInstallation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    try {
                        int installment= Integer.parseInt(Objects.requireNonNull(binding.numberofInstallation.getText()).toString().trim());
                        if(installment==0){
                            binding.numberofInstallation.setError("Installment can't be 0");
                        }else{
                            binding.numberofInstallation.setError(null);
                        }
                    } catch (NumberFormatException e) {
                        // Handle number input exception gracefully
                        e.printStackTrace();
                        binding.customerMobileEdt.setError("Invalid number format");
                    } catch (Exception e) {
                        // Catch any other exceptions to prevent crashing
                        e.printStackTrace();
                    }
                }
            }
        });
        binding.customerNidEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        String input = binding.customerNidEdt.getText().toString().trim();
                        if (input.isEmpty()) {
                            binding.customerNidEdt.setError("NID field cannot be empty");
                        } else if (!input.matches("^(\\d{10}|\\d{13}|\\d{17})$")) {
                            binding.customerNidEdt.setError("Please enter actual NID");
                        } else {
                            binding.customerNidEdt.setError(null);
                        }
                    } catch (NumberFormatException e) {
                        // Handle number input exception gracefully
                        e.printStackTrace();
                        binding.customerMobileEdt.setError("Invalid number format");
                    } catch (Exception e) {
                        // Catch any other exceptions to prevent crashing
                        e.printStackTrace();
                    }
                }
            }
        });

        binding.confirmSaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random= new Random();
                SharedPreferences sp = getSharedPreferences("device_details",MODE_PRIVATE);
                SharedPreferences sp2 = getSharedPreferences("saved_login",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String customerId = String.valueOf(1_000_000_000L + (long) (random.nextDouble() * 9_000_000_000L));
                editor.putString("customerId",customerId);
                String customerName = binding.customerNameEdt.getText().toString();
                editor.putString("customerName",customerName);
                String customerAddress = binding.customerAddressEdt.getText().toString();
                editor.putString("customerAddress",customerAddress);
                String customerNID = binding.customerNidEdt.getText().toString();
                editor.putString("customerNID",customerNID);
                String customerMobile = binding.customerMobileEdt.getText().toString();
                editor.putString("customerMobile",customerMobile);
                String retailerName = sp2.getString("retailerName","");
                editor.putString("retailerName",retailerName);
                String retailerId = String.valueOf(sp2.getInt("retailerId",0));
                editor.putString("retailerId",retailerId);
                int numberOfInstallment = Integer.parseInt(binding.numberofInstallation.getText().toString());
                editor.putInt("numberOfInstallment",numberOfInstallment);
                int downPayment = Integer.parseInt(binding.downPayment.getText().toString());
                editor.putInt("downPayment",downPayment);

                int randomNumber = 1000 + random.nextInt(9000);
                String posInvoiceNumber ="IISL-"+randomNumber;
                editor.putString("posInvoiceNumber",posInvoiceNumber);
                String downPaymentDate = binding.selectDate.getText().toString();
                editor.putString("downPaymentDate",downPaymentDate);

                String imei1 = sp.getString("imei1","");
                String barcode = sp.getString("barcode","");
                String brand = sp.getString("brand","");
                String model = sp.getString("model","");
                String color = sp.getString("color","");
                int hireSalePrice = Integer.parseInt(binding.hireSalePrice.getText().toString());
                editor.putInt("hireSalePrice",hireSalePrice);
                editor.apply();
                String salesBy = sp2.getString("retailerName","");
                String salesPersonName = sp2.getString("retailerName","");

                SaleRequestModel request = new SaleRequestModel(customerId,customerName,customerAddress,customerNID,customerMobile,salesBy,retailerName,retailerId,posInvoiceNumber,salesPersonName,imei1,barcode,brand,model,color,hireSalePrice,numberOfInstallment,downPayment,downPaymentDate);

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
        String text7=binding.hireSalePrice.getText().toString();
        String text9=binding.downPayment.getText().toString();
        String text10=binding.numberofInstallation.getText().toString();
        if(!text1.isEmpty() && !text2.isEmpty() && !text3.isEmpty() && !text4.isEmpty()&& !text7.isEmpty() && !text9.isEmpty() && !text10.isEmpty()){
            binding.confirmSaleBtn.setVisibility(View.VISIBLE);
        }else{
            binding.confirmSaleBtn.setVisibility(View.GONE);
        }
    }

    private void getInvoiceSuccess(String auth, SaleRequestModel req){
        binding.confirmSaleBtn.setVisibility(View.GONE);
        binding.confirmSaleProgess.setVisibility(View.VISIBLE);
        Call<SaleResponseModel> call = ApiController
                .getInstance()
                .getapi()
                .sendRequest(auth,req);
        call.enqueue(new Callback<SaleResponseModel>() {
            @Override
            public void onResponse(Call<SaleResponseModel> call, Response<SaleResponseModel> response) {

                binding.confirmSaleProgess.setVisibility(View.GONE);
                binding.confirmSaleBtn.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    SaleResponseModel responseData = response.body();
                    if ("Action Successful".equals(responseData.getDataObject().getMessage())) {
                        String nextInstallmentDate = responseData.getDataObject().getNext_installment_date();
                        SharedPreferences sp = getSharedPreferences("device_details",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("nextInstallmentDate",nextInstallmentDate);
                        editor.apply();
                        Toast.makeText(SaleCustomerInfoActivity.this, "Action Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SaleCustomerInfoActivity.this,InvoiceActivity.class));
                        finish();

                    } else if ("Action Unsuccessful".equals(responseData.getDataObject().getMessage())) {
                        Toast.makeText(SaleCustomerInfoActivity.this, "Action Unsuccessful", Toast.LENGTH_SHORT).show();
                    }else if ("Device Already Exist".equals(responseData.getDataObject().getMessage())) {
                        Toast.makeText(SaleCustomerInfoActivity.this, "Device Already Exist", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SaleCustomerInfoActivity.this, "Action Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SaleCustomerInfoActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<SaleResponseModel> call, Throwable t) {
                binding.confirmSaleProgess.setVisibility(View.GONE);
                binding.confirmSaleBtn.setVisibility(View.VISIBLE);
                Toast.makeText(SaleCustomerInfoActivity.this,  "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}