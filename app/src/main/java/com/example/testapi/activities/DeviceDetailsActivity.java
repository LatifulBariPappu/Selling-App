package com.example.testapi.activities;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityDeviceDetailsBinding;
import com.example.testapi.models.EmiScheduleModel;
import com.example.testapi.models.HistoryAdapter;
import com.example.testapi.models.HistoryModel;
import com.example.testapi.models.InstallmentAdapter;
import com.example.testapi.models.LockDeviceModel;
import com.example.testapi.models.PaymentModel;
import com.example.testapi.models.PaymentRequest;
import com.example.testapi.models.PolicyResponseModel;
import com.example.testapi.models.RestrictedPolicyRequestModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceDetailsActivity extends AppCompatActivity {

    ActivityDeviceDetailsBinding binding;
    InstallmentAdapter installmentAdapter;
    RecyclerView reminderRecView,devicePaymentRecView;

    private TextView nextPayAmountTv,nextPayDateTv,deviceStatusTv,nidTv,totalPaymentTv,totalDueTv,nameTv,mobileTv,dateTv,modelTv,imei1Tv,imei2Tv,lastSyncTv,totalDefaultedAmountTv,defaultedDateTv,remainingToPayTv,reminderImeiTv,historyImeiTv;
    private Button payBtn, restrictedBtn,lockBtn;
    private ImageView qrCodeIV;
    Boolean isDeviceClicked,isReminderClicked;
    CheckBox checkboxUsbDebugging,checkboxCall,checkboxSms,checkboxScreenCapture,checkboxUsbFile,checkboxMicrophone,checkboxCamera,checkboxMobileData,checkboxFacebook,checkboxWhatsapp,checkboxImo,checkboxViber,checkboxSnapchat,checkboxTelegram,checkboxMessenger,checkboxYoutube,checkboxSkype;
    int paymentAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.deviceTransition.setVisibility(View.VISIBLE);

        getDataOfDeviceContent();


//        checkboxUsbDebugging.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                Toast.makeText(this, "Option One Checked", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Option One Unchecked", Toast.LENGTH_SHORT).show();
//            }
//        });


        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.tabDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataOfDeviceContent();
            }
        });
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentPopup();
            }
        });
        binding.tabReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContentView(R.layout.reminder_content);
                getDataOfReminderContent();
            }
        });
        binding.tabAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContentView(R.layout.agreement_content);
                binding.deviceTransition.setVisibility(View.GONE);
                binding.reminderTransition.setVisibility(View.GONE);
                binding.payHistoryTransition.setVisibility(View.GONE);
                binding.agreementTransiton.setVisibility(View.VISIBLE);
            }
        });
        binding.tabPayHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContentView(R.layout.pay_history_content);
                getDataOfPaymentHistory();
            }
        });

        restrictedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRestricted();
            }
        });
        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("defaulters", Context.MODE_PRIVATE);
                String isDefaulter = sp.getString("isDefaulter","");
                if("yes".equals(isDefaulter)){
                    String defaulterImei1 = getIntent().getStringExtra("defaulterImei1");
                    lock_device(defaulterImei1);
                }else if("no".equals(isDefaulter)){
                    String imei1 = getIntent().getStringExtra("imei1");
                    lock_device(imei1);
                }

            }
        });
    }

    private void getRestricted(){
        SharedPreferences sp = getSharedPreferences("defaulters", Context.MODE_PRIVATE);
        String isDefaulter = sp.getString("isDefaulter","");
        if("yes".equals(isDefaulter)){
            String defaulterImei1 = getIntent().getStringExtra("defaulterImei1");
            RestrictedPolicyRequestModel model = setModelValues(defaulterImei1);
            callRestrictedApi(defaulterImei1,model);
        }else if("no".equals(isDefaulter)){
            String imei1 = getIntent().getStringExtra("imei1");
            RestrictedPolicyRequestModel model = setModelValues(imei1);
            callRestrictedApi(imei1,model);
        }
    }

    private void callRestrictedApi(String imei,RestrictedPolicyRequestModel model){
        Call<PolicyResponseModel> call = ApiController
                .getInstance()
                .getapi()
                .applyDeviceWisePolicy(imei, model);

        call.enqueue(new Callback<PolicyResponseModel>() {
            @Override
            public void onResponse(Call<PolicyResponseModel> call, Response<PolicyResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PolicyResponseModel responseBody = response.body();
                    int statusCode = responseBody.getStatusCode();

                    switch (statusCode) {
                        case 200: // Success
                            Toast.makeText(getApplicationContext(), "Restriction applied successfully", Toast.LENGTH_SHORT).show();
                            break;
                        case 404: // Device Not Found
                            Toast.makeText(getApplicationContext(), "Device Not Found!", Toast.LENGTH_SHORT).show();
                            break;

                        case 500: // Internal Server Error
                            Toast.makeText(getApplicationContext(), "Error Occurred: " + responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                            break;

                        case 422: // Validation Errors
                            Map<String, List<String>> validationErrors = responseBody.getValidationErrors();
                            if (validationErrors != null && !validationErrors.isEmpty()) {
                                StringBuilder errorMessage = new StringBuilder("Validation Errors:\n");
                                for (Map.Entry<String, List<String>> entry : validationErrors.entrySet()) {
                                    errorMessage.append(entry.getKey()).append(": ").append(entry.getValue().get(0)).append("\n");
                                }
                                Toast.makeText(getApplicationContext(), errorMessage.toString().trim(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Validation errors occurred.", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        default: // Other cases
                            Toast.makeText(getApplicationContext(), "Unexpected status: " + responseBody.getStatus(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    // Response was not successful or body was null
                    Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PolicyResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Bitmap generateQRCode(String data) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            // Set dimensions and encoding format
            int width = 512, height = 512;
            com.google.zxing.common.BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height);

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return bitmap;

        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadContentView(int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(layoutId, null);

        // Replace the content in the FrameLayout
        binding.frameLayout.removeAllViews();
        binding.frameLayout.addView(contentView);
        initializeViews(contentView);
    }
    private void getDataOfDeviceContent(){
        isDeviceClicked = true;
        isReminderClicked = false;
        loadContentView(R.layout.device_content);
        binding.deviceTransition.setVisibility(View.VISIBLE);
        binding.reminderTransition.setVisibility(View.GONE);
        binding.agreementTransiton.setVisibility(View.GONE);
        binding.payHistoryTransition.setVisibility(View.GONE);

        initializeViews(binding.frameLayout.getChildAt(0));

        SharedPreferences sp = getSharedPreferences("defaulters", Context.MODE_PRIVATE);
        String isDefaulter = sp.getString("isDefaulter","");
        if("yes".equals(isDefaulter)){
            modelTv.setVisibility(View.GONE);
            lastSyncTv.setVisibility(View.GONE);
            deviceStatusTv.setVisibility(View.GONE);
            nextPayAmountTv.setVisibility(View.GONE);
            nextPayDateTv.setVisibility(View.GONE);
            totalDefaultedAmountTv.setVisibility(View.VISIBLE);
            defaultedDateTv.setVisibility(View.VISIBLE);
            remainingToPayTv.setVisibility(View.VISIBLE);

            String defaulterImei1 = getIntent().getStringExtra("defaulterImei1");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("defaulterImei1",defaulterImei1);
            editor.apply();
            String defaulterImei2 = getIntent().getStringExtra("defaulterImei2");
            String defaulterName = getIntent().getStringExtra("defaulterName");
            String defaulterMobile = getIntent().getStringExtra("defaulterMobile");
            int totalDefaultedAmount = getIntent().getIntExtra("totalDefaultedAmount",0);
            String defaulterDownPaymentDate = getIntent().getStringExtra("defaulterDownPaymentDate");
            String defaultedDate = getIntent().getStringExtra("defaultedDate");
            int remainingToPay = getIntent().getIntExtra("remainingToPay",0);
            long nid = getIntent().getLongExtra("nid",0);
            nameTv.setText(defaulterName);
            imei1Tv.setText("IMEI1 : "+defaulterImei1);
            imei2Tv.setText("IMEI2 : "+defaulterImei2);
            mobileTv.setText(defaulterMobile);
            dateTv.setText("Defaulted on\n"+defaultedDate);
            if(paymentAmount>0){
                totalDefaultedAmount-=paymentAmount;
                remainingToPay-=paymentAmount;
            }
            nidTv.setText("NID : "+nid);
            totalDefaultedAmountTv.setText("Total Defaulted Amount : "+totalDefaultedAmount);
            defaultedDateTv.setText("Defaulted downPay date : "+defaulterDownPaymentDate);
            remainingToPayTv.setText("Remaining To Pay : "+remainingToPay);

            Bitmap qrCodeBitmap = generateQRCode(defaulterImei1);
            if (qrCodeBitmap != null) {
                qrCodeIV.setImageBitmap(qrCodeBitmap);
            }

        }else if("no".equals(isDefaulter)){
            modelTv.setVisibility(View.VISIBLE);
            lastSyncTv.setVisibility(View.VISIBLE);
            totalPaymentTv.setVisibility(View.VISIBLE);
            totalDueTv.setVisibility(View.VISIBLE);
            deviceStatusTv.setVisibility(View.VISIBLE);
            nextPayAmountTv.setVisibility(View.VISIBLE);
            nextPayDateTv.setVisibility(View.VISIBLE);

            totalDefaultedAmountTv.setVisibility(View.GONE);
            defaultedDateTv.setVisibility(View.GONE);
            remainingToPayTv.setVisibility(View.GONE);
            String imei1 = getIntent().getStringExtra("imei1");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("imei1",imei1);
            editor.apply();
            String imei2 = getIntent().getStringExtra("imei2");
            String mobile = getIntent().getStringExtra("mobile");
            String name = getIntent().getStringExtra("name");
            String date = getIntent().getStringExtra("downPayDate");
            String model = getIntent().getStringExtra("model");
            String lastSync = getIntent().getStringExtra("lastSync");
            long nid = getIntent().getLongExtra("nid",0);
            int totalPayment = getIntent().getIntExtra("totalPayment",0);
            int totalDue = getIntent().getIntExtra("totalDue",0);
            String deviceStatus = getIntent().getStringExtra("deviceStatus");
            String nextPayDate = getIntent().getStringExtra("nextPayDate");
            String nextPayAmount = getIntent().getStringExtra("nextPayAmount");
            nextPayAmountTv.setText("Next Payment Amount : "+nextPayAmount);
            nextPayDateTv.setText("Next Payment Date : "+nextPayDate);
            if ("Unlock".equals(deviceStatus)){
                deviceStatusTv.setText("Device Status : Unlock");
            }else{
                deviceStatusTv.setText("Device Status : Lock");
            }
            nidTv.setText("NID : "+nid);
            totalPaymentTv.setText("Total Payment : "+totalPayment);
            totalDueTv.setText("Total Due : "+totalDue);
            nameTv.setText(name);
            imei1Tv.setText("IMEI1 : "+imei1);
            imei2Tv.setText("IMEI2 : "+imei2);
            mobileTv.setText(mobile);
            dateTv.setText("Down pay date\n"+date);
            modelTv.setText("Model : "+model);
            lastSyncTv.setText("Last Sync : "+lastSync);
            Bitmap qrCodeBitmap = generateQRCode(imei1);
            if (qrCodeBitmap != null) {
                qrCodeIV.setImageBitmap(qrCodeBitmap);
            }

        }

    }
    private void  getDataOfPaymentHistory(){
        binding.deviceTransition.setVisibility(View.GONE);
        binding.reminderTransition.setVisibility(View.GONE);
        binding.payHistoryTransition.setVisibility(View.VISIBLE);
        binding.agreementTransiton.setVisibility(View.GONE);
        initializeViews(binding.frameLayout.getChildAt(0));
        SharedPreferences sp = getSharedPreferences("defaulters", Context.MODE_PRIVATE);
        String isDefaulter = sp.getString("isDefaulter","");
        if("yes".equals(isDefaulter)){
            String defaulterImei1 = getIntent().getStringExtra("defaulterImei1");
            //call reminder api according to defaulter imei1
            getPayHistory(defaulterImei1);

        }else if("no".equals(isDefaulter)){
            String imei1 = getIntent().getStringExtra("imei1");
            //call reminder api according to imei1
            getPayHistory(imei1);
        }
    }
    private void getDataOfReminderContent(){
        isReminderClicked = true;
        isDeviceClicked = false;
        loadContentView(R.layout.reminder_content);
        binding.deviceTransition.setVisibility(View.GONE);
        binding.reminderTransition.setVisibility(View.VISIBLE);
        binding.payHistoryTransition.setVisibility(View.GONE);
        binding.agreementTransiton.setVisibility(View.GONE);
        initializeViews(binding.frameLayout.getChildAt(0));
        SharedPreferences sp = getSharedPreferences("defaulters", Context.MODE_PRIVATE);
        String isDefaulter = sp.getString("isDefaulter","");
        if("yes".equals(isDefaulter)){
            String defaulterImei1 = getIntent().getStringExtra("defaulterImei1");
            //call reminder api according to defaulter imei1
            getSchedule(defaulterImei1);

        }else if("no".equals(isDefaulter)){
            String imei1 = getIntent().getStringExtra("imei1");
            //call reminder api according to imei1
            getSchedule(imei1);
        }

    }

    private void getSchedule(String imei1){
        reminderImeiTv.setText("Installments for IMEI1 ( "+imei1+" ) scheduled below :");
        Call<EmiScheduleModel> call = ApiController
                .getInstance()
                .getapi()
                .getEmiSchedule(imei1);

        call.enqueue(new Callback<EmiScheduleModel>() {
            @Override
            public void onResponse(Call<EmiScheduleModel> call, Response<EmiScheduleModel> response) {
                if(response.isSuccessful() && response.body()!=null){
                    String msg = response.body().getMessage();
                    if("EMI schedule retrieved successfully".equals(msg)){
                        Toast.makeText(DeviceDetailsActivity.this, "emi schedule retrieved successfully", Toast.LENGTH_SHORT).show();
                        List<EmiScheduleModel.EmiSchedule> emiScheduleList = response.body().getEmiScheduleList();
                        installmentAdapter = new InstallmentAdapter(emiScheduleList);
                        reminderRecView.setAdapter(installmentAdapter);
                    }
                }else{
                    Toast.makeText(DeviceDetailsActivity.this, "emi schedule response body is null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<EmiScheduleModel> call, Throwable t) {
                Toast.makeText(DeviceDetailsActivity.this, "Failed to fetch EMI schedule", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initializeViews(View view) {
        nameTv = findViewById(R.id.customerNameTV);
        mobileTv = findViewById(R.id.customerMobileTV);
        imei1Tv = findViewById(R.id.imei1TV);
        imei2Tv = findViewById(R.id.imei2TV);
        dateTv = findViewById(R.id.dateTV);
        modelTv = findViewById(R.id.modelTV);
        lastSyncTv = findViewById(R.id.lastSyncTV);
        totalDefaultedAmountTv = findViewById(R.id.totalDefaultedAmountTV);
        defaultedDateTv = findViewById(R.id.defaultedDateTV);
        remainingToPayTv = findViewById(R.id.remainingToPayTV);
        reminderImeiTv = findViewById(R.id.reminderImeiTv);
        historyImeiTv = findViewById(R.id.historyImeiTv);
        reminderRecView = view.findViewById(R.id.reminderRecView);
        nidTv = view.findViewById(R.id.customerNidTv);
        totalPaymentTv = view.findViewById(R.id.totalPaymentTV);
        totalDueTv = view.findViewById(R.id.totalDueTV);
        devicePaymentRecView = view.findViewById(R.id.devicePaymentRecView);
        deviceStatusTv=view.findViewById(R.id.deviceStatusTV);
        qrCodeIV = view.findViewById(R.id.deviceQrCodeIV);
        nextPayAmountTv = view.findViewById(R.id.nextPayAmountTV);
        nextPayDateTv = view.findViewById(R.id.nextPayDataTV);
        restrictedBtn = view.findViewById(R.id.restrictedBtn);
        lockBtn=view.findViewById(R.id.lockBtn);

        if (devicePaymentRecView != null) {
            devicePaymentRecView.setLayoutManager(new LinearLayoutManager(this));
        }
        if (reminderRecView != null) {
            reminderRecView.setLayoutManager(new LinearLayoutManager(this));
        }
        payBtn = findViewById(R.id.paymentBtn);

        checkboxUsbDebugging = view.findViewById(R.id.checkboxUsbDebugging);
        checkboxCall = view.findViewById(R.id.checkboxCall);
        checkboxSms = view.findViewById(R.id.checkboxSms);
        checkboxScreenCapture = view.findViewById(R.id.checkboxScreenCapture);
        checkboxUsbFile = view.findViewById(R.id.checkboxUsbFile);
        checkboxMicrophone = view.findViewById(R.id.checkboxMicrophone);
        checkboxCamera = view.findViewById(R.id.checkboxCamera);
        checkboxMobileData = view.findViewById(R.id.checkboxMobileData);
        checkboxFacebook = view.findViewById(R.id.checkboxFacebook);
        checkboxWhatsapp = view.findViewById(R.id.checkboxWhatsapp);
        checkboxImo = view.findViewById(R.id.checkboxImo);
        checkboxViber = view.findViewById(R.id.checkboxViber);
        checkboxSnapchat = view.findViewById(R.id.checkboxSnapchat);
        checkboxTelegram = view.findViewById(R.id.checkboxTelegram);
        checkboxMessenger = view.findViewById(R.id.checkboxMessenger);
        checkboxYoutube = view.findViewById(R.id.checkboxYoutube);
        checkboxSkype = view.findViewById(R.id.checkboxSkype);
    }
    private void showPaymentPopup() {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.payment_popup, null);

        String imei = "";
        TextView paymentImeiTv = popupView.findViewById(R.id.paymentImeiTv);
        TextView paymentDateTv = popupView.findViewById(R.id.paymentDateTv);
        Button confirmPaymentBtn = popupView.findViewById(R.id.confirmPaymentBtn);
        EditText paymentAmountEdt = popupView.findViewById(R.id.paymentAmountEdt);
        ProgressBar confirmPaymentProgress = popupView.findViewById(R.id.confirmPaymentProgress);
        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView);
        AlertDialog dialog = builder.create();
        dialog.show();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        paymentDateTv.setText(currentDate);


        SharedPreferences sp = getSharedPreferences("defaulters", Context.MODE_PRIVATE);
        String isDefaulter = sp.getString("isDefaulter","");
        if("yes".equals(isDefaulter)){
            String defaulterImei1 = sp.getString("defaulterImei1","") ;
            imei = defaulterImei1;
            paymentImeiTv.setText("Make payment for IMEI1 ( "+defaulterImei1+" )");
        }else if("no".equals(isDefaulter)){
            String imei1 = sp.getString("imei1","");
            imei = imei1;
            paymentImeiTv.setText("Make payment for IMEI1 ( "+imei1+" )");
        }
        paymentDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Show DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(DeviceDetailsActivity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            // Update the TextView with the selected date
                            String selectedDate = selectedYear + "-" + String.format("%02d", (selectedMonth + 1)) + "-" + String.format("%02d", selectedDay);
                            paymentDateTv.setText(selectedDate);
                        }, year, month, day);

                // Set the maximum date to today
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        String finalImei = imei;
        confirmPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentAmountInput = paymentAmountEdt.getText().toString().trim();
                // Validate if the payment amount field is empty
                if (paymentAmountInput.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a payment amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Parse the input to an integer
                    paymentAmount = Integer.parseInt(paymentAmountInput);

                    // Check if payment amount is valid (e.g., not zero or negative)
                    if (paymentAmount <= 0) {
                        Toast.makeText(getApplicationContext(), "Payment amount must be greater than 0", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid payment amount. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Validate the payment date
                String paymentDate = paymentDateTv.getText().toString();
                if (paymentDate.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select a payment date", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Parse the selected payment date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date selectedDate = sdf.parse(paymentDate);
                    Date currentDate = new Date();

                    // Check if the selected date is after the current date
                    if (selectedDate != null && selectedDate.after(currentDate)) {
                        Toast.makeText(getApplicationContext(), "Payment date cannot be in the future", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Invalid payment date format", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Create the payment request
                PaymentRequest request = new PaymentRequest(finalImei,paymentAmount,paymentDate);
                String authorization = "iF3PTw5zRS7JdKeu2ULE3A==";

                // Call the payment API
                confirmPaymentBtn.setVisibility(View.GONE);
                confirmPaymentProgress.setVisibility(View.VISIBLE);
                Call<PaymentModel> call = ApiController
                        .getInstance()
                        .getapi()
                        .getPayment(authorization,request);
                call.enqueue(new Callback<PaymentModel>() {
                    @Override
                    public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
                        confirmPaymentBtn.setVisibility(View.VISIBLE);
                        confirmPaymentProgress.setVisibility(View.GONE);
                        if(response.isSuccessful() && response.body()!=null){
                            String msg = response.body().getData().getMessage();
                            if("Payment Successful".equals(msg)){
                                Toast.makeText(getApplicationContext(),"Payment Successful",Toast.LENGTH_SHORT).show();
                                updateDefaultedAmount();
                                dialog.dismiss();

                            } else if ("Device Dose not Exist".equals(msg)) {
                                Toast.makeText(getApplicationContext(), "Device Dose not Exist", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else if ("Payment Unsuccessful".equals(msg)) {
                                Toast.makeText(getApplicationContext(), "Payment amount and date is required", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"response body is null",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentModel> call, Throwable t) {
                        confirmPaymentBtn.setVisibility(View.VISIBLE);
                        confirmPaymentProgress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Failed : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });

    }
    private void updateDefaultedAmount() {
        // Retrieve the current amounts
        int totalDefaultedAmount = Integer.parseInt(totalDefaultedAmountTv.getText().toString().replaceAll("[^\\d]", ""));
        int remainingToPay = Integer.parseInt(remainingToPayTv.getText().toString().replaceAll("[^\\d]", ""));

        // Subtract the payment amount
        totalDefaultedAmount -= paymentAmount;
        remainingToPay -= paymentAmount;

        // Update the TextViews
        totalDefaultedAmountTv.setText("Total Defaulted Amount : " + totalDefaultedAmount);
        remainingToPayTv.setText("Remaining To Pay : " + remainingToPay);
    }
    private void getPayHistory(String imei){
        historyImeiTv.setText("Payments for IMEI1 ( "+imei+" ) listed below :");
        Call<HistoryModel> call =
                ApiController.getInstance()
                        .getapi().getPaymentHistory(imei);
        call.enqueue(new Callback<HistoryModel>() {
            @Override
            public void onResponse(Call<HistoryModel> call, Response<HistoryModel> response) {
                if (response.isSuccessful() && response.body() != null){
                    String msg = response.body().getMessage();
                    if("Payment history retrieved successfully.".equals(msg)){
                        Toast.makeText(getApplicationContext(), "Payment history retrieved successfully.", Toast.LENGTH_SHORT).show();
                        List<HistoryModel.data> data =response.body().getData();
                        devicePaymentRecView.setAdapter(new HistoryAdapter(data));
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Payment history response body is null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<HistoryModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private RestrictedPolicyRequestModel setModelValues(String imei) {
        RestrictedPolicyRequestModel policyConfig = new RestrictedPolicyRequestModel();

        List<RestrictedPolicyRequestModel.Policy> policies = new ArrayList<>();
        List<RestrictedPolicyRequestModel.AppPolicy> appPolicies = new ArrayList<>();
        List<RestrictedPolicyRequestModel.NetworkPolicy> networkPolicies = new ArrayList<>();
        //handle policy
        if (checkboxUsbDebugging.isChecked()) {
            RestrictedPolicyRequestModel.Policy usbPolicy = new RestrictedPolicyRequestModel.Policy();
            usbPolicy.setPolicyName("usbdebugging1");
            usbPolicy.setImei(imei);
            policies.add(usbPolicy);
        }else if (!checkboxUsbDebugging.isChecked()){
            RestrictedPolicyRequestModel.Policy usbPolicy = new RestrictedPolicyRequestModel.Policy();
            usbPolicy.setPolicyName("usbdebugging");
            usbPolicy.setImei(imei);
            policies.add(usbPolicy);
        }
        if (checkboxScreenCapture.isChecked()) {
            RestrictedPolicyRequestModel.Policy screenPolicy = new RestrictedPolicyRequestModel.Policy();
            screenPolicy.setPolicyName("screencapture");
            screenPolicy.setImei(imei);
            policies.add(screenPolicy);
        }

        if (checkboxCall.isChecked()) {
            RestrictedPolicyRequestModel.Policy callPolicy = new RestrictedPolicyRequestModel.Policy();
            callPolicy.setPolicyName("outgoingcall");
            callPolicy.setImei(imei);
            policies.add(callPolicy);
        }

        if (checkboxSms.isChecked()) {
            RestrictedPolicyRequestModel.Policy callPolicy = new RestrictedPolicyRequestModel.Policy();
            callPolicy.setPolicyName("sms");
            callPolicy.setImei(imei);
            policies.add(callPolicy);
        }
        if (checkboxMicrophone.isChecked()) {
            RestrictedPolicyRequestModel.Policy microphonePolicy = new RestrictedPolicyRequestModel.Policy();
            microphonePolicy.setPolicyName("microphone");
            microphonePolicy.setImei(imei);
            policies.add(microphonePolicy);
        }
        if (checkboxUsbFile.isChecked()) {
            RestrictedPolicyRequestModel.Policy usbFilePolicy = new RestrictedPolicyRequestModel.Policy();
            usbFilePolicy.setPolicyName("filetransfer");
            usbFilePolicy.setImei(imei);
            policies.add(usbFilePolicy);
        }else if(!checkboxUsbFile.isChecked()){
            RestrictedPolicyRequestModel.Policy usbFilePolicy = new RestrictedPolicyRequestModel.Policy();
            usbFilePolicy.setPolicyName("filetransfer1");
            usbFilePolicy.setImei(imei);
            policies.add(usbFilePolicy);
        }

        if (checkboxCamera.isChecked()) {
            RestrictedPolicyRequestModel.Policy cameraPolicy = new RestrictedPolicyRequestModel.Policy();
            cameraPolicy.setPolicyName("camera");
            cameraPolicy.setImei(imei);
            policies.add(cameraPolicy);
        }
        // Handle app policies
        if (checkboxFacebook.isChecked()) {
            RestrictedPolicyRequestModel.AppPolicy facebookPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            facebookPolicy.setAppName("facebook");
            facebookPolicy.setPackageName("com.facebook.katana");
            facebookPolicy.setImei(imei);
            facebookPolicy.setAppStatus("BLOCKED");
            appPolicies.add(facebookPolicy);
        }else if (!checkboxFacebook.isChecked()) {
            RestrictedPolicyRequestModel.AppPolicy facebookPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            facebookPolicy.setAppName("facebook");
            facebookPolicy.setPackageName("com.facebook.katana");
            facebookPolicy.setImei(imei);
            facebookPolicy.setAppStatus("FORCE_INSTALLED");
            appPolicies.add(facebookPolicy);
        }

        if (checkboxImo.isChecked()) {
            RestrictedPolicyRequestModel.AppPolicy imoPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            imoPolicy.setAppName("imo");
            imoPolicy.setPackageName("com.imo.android.imoim");
            imoPolicy.setImei(imei);
            imoPolicy.setAppStatus("BLOCKED");
            appPolicies.add(imoPolicy);
        }else if (!checkboxImo.isChecked()){
            RestrictedPolicyRequestModel.AppPolicy imoPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            imoPolicy.setAppName("imo");
            imoPolicy.setPackageName("com.imo.android.imoim");
            imoPolicy.setImei(imei);
            imoPolicy.setAppStatus("FORCE_INSTALLED");
            appPolicies.add(imoPolicy);
        }
        if (checkboxWhatsapp.isChecked()) {
            RestrictedPolicyRequestModel.AppPolicy whatsappPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            whatsappPolicy.setAppName("whatsapp");
            whatsappPolicy.setPackageName("com.whatsapp");
            whatsappPolicy.setImei(imei);
            whatsappPolicy.setAppStatus("BLOCKED");
            appPolicies.add(whatsappPolicy);
        }else if (!checkboxWhatsapp.isChecked()){
            RestrictedPolicyRequestModel.AppPolicy whatsappPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            whatsappPolicy.setAppName("whatsapp");
            whatsappPolicy.setPackageName("com.whatsapp");
            whatsappPolicy.setImei(imei);
            whatsappPolicy.setAppStatus("FORCE_INSTALLED");
            appPolicies.add(whatsappPolicy);
        }

        if (checkboxViber.isChecked()) {
            RestrictedPolicyRequestModel.AppPolicy viberPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            viberPolicy.setAppName("viber");
            viberPolicy.setPackageName("com.viber.voip");
            viberPolicy.setImei(imei);
            viberPolicy.setAppStatus("BLOCKED");
            appPolicies.add(viberPolicy);
        }else if (!checkboxViber.isChecked()){
            RestrictedPolicyRequestModel.AppPolicy viberPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            viberPolicy.setAppName("viber");
            viberPolicy.setPackageName("com.viber.voip");
            viberPolicy.setImei(imei);
            viberPolicy.setAppStatus("FORCE_INSTALLED");
            appPolicies.add(viberPolicy);
        }
        if (checkboxSnapchat.isChecked()) {
            RestrictedPolicyRequestModel.AppPolicy snapchatPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            snapchatPolicy.setAppName("snapchat");
            snapchatPolicy.setPackageName("com.snapchat.android");
            snapchatPolicy.setImei(imei);
            snapchatPolicy.setAppStatus("BLOCKED");
            appPolicies.add(snapchatPolicy);
        }else if (!checkboxSnapchat.isChecked()){
            RestrictedPolicyRequestModel.AppPolicy snapchatPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            snapchatPolicy.setAppName("snapchat");
            snapchatPolicy.setPackageName("com.snapchat.android");
            snapchatPolicy.setImei(imei);
            snapchatPolicy.setAppStatus("FORCE_INSTALLED");
            appPolicies.add(snapchatPolicy);
        }
        if (checkboxTelegram.isChecked()) {
            RestrictedPolicyRequestModel.AppPolicy telegramPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            telegramPolicy.setAppName("telegram");
            telegramPolicy.setPackageName("org.telegram.messenger");
            telegramPolicy.setImei(imei);
            telegramPolicy.setAppStatus("BLOCKED");
            appPolicies.add(telegramPolicy);
        }else if (!checkboxTelegram.isChecked()){
            RestrictedPolicyRequestModel.AppPolicy telegramPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            telegramPolicy.setAppName("telegram");
            telegramPolicy.setPackageName("org.telegram.messenger");
            telegramPolicy.setImei(imei);
            telegramPolicy.setAppStatus("FORCE_INSTALLED");
            appPolicies.add(telegramPolicy);
        }

        if (checkboxMessenger.isChecked()) {
            RestrictedPolicyRequestModel.AppPolicy messengerPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            messengerPolicy.setAppName("messenger");
            messengerPolicy.setPackageName("com.facebook.orca");
            messengerPolicy.setImei(imei);
            messengerPolicy.setAppStatus("BLOCKED");
            appPolicies.add(messengerPolicy);
        }else if (!checkboxMessenger.isChecked()){
            RestrictedPolicyRequestModel.AppPolicy messengerPolicy = new RestrictedPolicyRequestModel.AppPolicy();
            messengerPolicy.setAppName("messenger");
            messengerPolicy.setPackageName("com.facebook.orca");
            messengerPolicy.setImei(imei);
            messengerPolicy.setAppStatus("FORCE_INSTALLED");
            appPolicies.add(messengerPolicy);
        }
        if (checkboxYoutube.isChecked()) {
            RestrictedPolicyRequestModel.AppPolicy youtubePolicy = new RestrictedPolicyRequestModel.AppPolicy();
            youtubePolicy.setAppName("youtube");
            youtubePolicy.setPackageName("com.google.android.youtube");
            youtubePolicy.setImei(imei);
            youtubePolicy.setAppStatus("BLOCKED");
            appPolicies.add(youtubePolicy);
        }else if (!checkboxYoutube.isChecked()){
            RestrictedPolicyRequestModel.AppPolicy youtubePolicy = new RestrictedPolicyRequestModel.AppPolicy();
            youtubePolicy.setAppName("youtube");
            youtubePolicy.setPackageName("com.google.android.youtube");
            youtubePolicy.setImei(imei);
            youtubePolicy.setAppStatus("FORCE_INSTALLED");
            appPolicies.add(youtubePolicy);
        }
        if (checkboxSkype.isChecked()) {
            RestrictedPolicyRequestModel.AppPolicy skypePolicy = new RestrictedPolicyRequestModel.AppPolicy();
            skypePolicy.setAppName("skype");
            skypePolicy.setPackageName("com.skype.raider");
            skypePolicy.setImei(imei);
            skypePolicy.setAppStatus("BLOCKED");
            appPolicies.add(skypePolicy);
        }else if (!checkboxSkype.isChecked()){
            RestrictedPolicyRequestModel.AppPolicy skypePolicy = new RestrictedPolicyRequestModel.AppPolicy();
            skypePolicy.setAppName("skype");
            skypePolicy.setPackageName("com.skype.raider");
            skypePolicy.setImei(imei);
            skypePolicy.setAppStatus("FORCE_INSTALLED");
            appPolicies.add(skypePolicy);
        }

        //handle network policy

        if (checkboxMobileData.isChecked()){
            RestrictedPolicyRequestModel.NetworkPolicy mobilePolicy = new RestrictedPolicyRequestModel.NetworkPolicy();
            mobilePolicy.setPolicyName("mobilenet");
            mobilePolicy.setImei(imei);
            networkPolicies.add(mobilePolicy);
        }

        policyConfig.setPolicy(policies);
        policyConfig.setApppolicy(appPolicies);
        policyConfig.setNetworkpolicy(networkPolicies);

        return policyConfig;
    }
    private void lock_device(String imei1){
        Call<LockDeviceModel> call = ApiController
                .getInstance()
                .getapi()
                .lockDevice(imei1);

        call.enqueue(new Callback<LockDeviceModel>() {
            @Override
            public void onResponse(Call<LockDeviceModel> call, Response<LockDeviceModel> response) {
                if(response.isSuccessful() && response.body()!=null){
                    String status = response.body().getStatus();
                    if("Successful".equals(status)){
                        Toast.makeText(getApplicationContext(),"Device Locked Successfully",Toast.LENGTH_SHORT).show();
                    } else if ("Unsuccessful".equals(status)) {
                        Toast.makeText(getApplicationContext(),"Lock request not confirmed",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LockDeviceModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}