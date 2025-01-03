package com.example.testapi.activities;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.example.testapi.models.PaymentModel;
import com.example.testapi.models.PaymentRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceDetailsActivity extends AppCompatActivity {

    ActivityDeviceDetailsBinding binding;
    InstallmentAdapter installmentAdapter;
    RecyclerView reminderRecView,devicePaymentRecView;

    private TextView deviceStatusTv,nidTv,totalPaymentTv,totalDueTv,nameTv,mobileTv,dateTv,modelTv,imei1Tv,imei2Tv,lastSyncTv,totalDefaultedAmountTv,defaultedDateTv,remainingToPayTv,reminderImeiTv,historyImeiTv;
    private Button payBtn;
    private ImageView qrCodeIV;
    Boolean isDeviceClicked,isReminderClicked;
    int paymentAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.deviceTransition.setVisibility(View.VISIBLE);

        getDataOfDeviceContent();




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

        if (devicePaymentRecView != null) {
            devicePaymentRecView.setLayoutManager(new LinearLayoutManager(this));
        }
        if (reminderRecView != null) {
            reminderRecView.setLayoutManager(new LinearLayoutManager(this));
        }
        payBtn = findViewById(R.id.paymentBtn);
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

}