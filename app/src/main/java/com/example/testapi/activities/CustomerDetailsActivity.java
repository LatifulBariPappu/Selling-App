package com.example.testapi.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testapi.R;
import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityCustomerDetailsBinding;
import com.example.testapi.models.HistoryAdapter;
import com.example.testapi.models.HistoryModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity {

    ActivityCustomerDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.customerPaymentRecView.setLayoutManager(new LinearLayoutManager(this));

        binding.backCustomerDetail.setOnClickListener(v -> {
            finish();
        });

        ImageView qrCodeIV = findViewById(R.id.customerQrCodeIV);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        binding.customerDetailNameTv.setText(name);
        String mobile = intent.getStringExtra("mobile");
        binding.customerDetailMobileTv.setText(mobile);
        String id = intent.getStringExtra("id");
        binding.customerDetailIdTv.setText("Id : "+id);
        long nid = intent.getLongExtra("nid",0);
        binding.customerDetailNidTv.setText("NID : "+nid);
        String address = intent.getStringExtra("address");
        binding.customerDetailAddressTv.setText("Address : "+address);

        String imei1 = intent.getStringExtra("imei1");
        binding.customerDetailImei1Tv.setText("IMEI1 : "+imei1);
        String imei2 = intent.getStringExtra("imei2");
        binding.customerDetailImei2Tv.setText("IMEI2 : "+imei2);

        String serial = intent.getStringExtra("serial");
        binding.customerDetailSerialTv.setText("Serial : "+serial);

        String model = intent.getStringExtra("model");
        binding.customerDetailModelTv.setText("Model : "+model);
        String brand = intent.getStringExtra("brand");
        binding.customerDetailBrandTv.setText("Brand : "+brand);
        String color = intent.getStringExtra("color");
        binding.customerDetailColorTv.setText("Color : "+color);
        String lastSync = intent.getStringExtra("lastSync");
        binding.lastSyncTV.setText("Last Sync : "+lastSync);
        String downPayDate = intent.getStringExtra("downPayDate");
        binding.customerDetailDateTv.setText("Down pay date\n"+downPayDate);
        int totalPayment = intent.getIntExtra("totalPayment",0);
        binding.customerDetailTotalPaymentTV.setText("Total Payment : "+totalPayment);
        int totalDue = intent.getIntExtra("totalDue",0);
        binding.customerDetailTotalDueTV.setText("Total Due : "+totalDue);
        Bitmap qrCodeBitmap = generateQRCode(imei1);
        if (qrCodeBitmap != null) {
            qrCodeIV.setImageBitmap(qrCodeBitmap);
        }
        getPayHistory(imei1);
        SharedPreferences sp = getSharedPreferences("customers", Context.MODE_PRIVATE);
        String customerType = sp.getString("type", "");
        if (customerType.equals("good") || customerType.equals("happy")) {
            binding.customerDetailModelTv.setVisibility(View.GONE);
            binding.customerDetailBrandTv.setVisibility(View.GONE);
            binding.customerDetailColorTv.setVisibility(View.GONE);
            binding.customerDetailTotalPaymentTV.setVisibility(View.GONE);
            binding.customerDetailTotalDueTV.setVisibility(View.GONE);
            binding.lastSyncTV.setVisibility(View.GONE);
        } else if (customerType.equals("all")) {
            binding.customerDetailModelTv.setVisibility(View.VISIBLE);
            binding.customerDetailBrandTv.setVisibility(View.VISIBLE);
            binding.customerDetailColorTv.setVisibility(View.VISIBLE);
            binding.customerDetailTotalPaymentTV.setVisibility(View.VISIBLE);
            binding.customerDetailTotalDueTV.setVisibility(View.VISIBLE);
            binding.lastSyncTV.setVisibility(View.VISIBLE);

        }

    }
    private void getPayHistory(String imei){
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
                        binding.customerPaymentRecView.setAdapter(new HistoryAdapter(data));
                    } else if ("No payment history found for the provided IMEI.".equals(msg)) {
                        Toast.makeText(getApplicationContext(), "No payment history found for the provided IMEI.", Toast.LENGTH_SHORT).show();
                    }else if ("Validation errors".equals(msg)) {
                        Toast.makeText(getApplicationContext(), "Validation errors", Toast.LENGTH_SHORT).show();
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
}