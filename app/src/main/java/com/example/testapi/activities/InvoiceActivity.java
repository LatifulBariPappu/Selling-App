package com.example.testapi.activities;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapi.databinding.ActivityInvoiceBinding;

import java.io.OutputStream;

public class InvoiceActivity extends AppCompatActivity {
    ActivityInvoiceBinding binding;
    Uri pdfUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getAllInfo();

        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InvoiceActivity.this,RetailerHomeActivity.class));
            }
        });
        binding.invoiceDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdf();
                binding.toolbar.setVisibility(View.VISIBLE);
                binding.invoiceDownloadBtn.setVisibility(View.VISIBLE);
                binding.invoiceShareBtn.setVisibility(View.VISIBLE);
            }
        });
        binding.invoiceShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf();
            }
        });
    }
    private void createPdf() {
        binding.toolbar.setVisibility(View.GONE);
        binding.invoiceDownloadBtn.setVisibility(View.GONE);
        binding.invoiceShareBtn.setVisibility(View.GONE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        View rootView = getWindow().getDecorView().getRootView();

        // Create PDF document
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(rootView.getWidth(), rootView.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        rootView.draw(canvas);
        document.finishPage(page);

        // Save to Downloads folder using MediaStore API
        OutputStream fos = null;


        try {
            String fileName = "firstInstallmentInvoice.pdf";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Use MediaStore for Android 10 and above
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                pdfUri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (pdfUri != null) {
                    fos = getContentResolver().openOutputStream(pdfUri);
                }
            } else {
                // For Android 9 and below, save directly to Downloads folder
                String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                pdfUri = Uri.parse(downloadsPath + "/" + fileName);
                fos = new java.io.FileOutputStream(pdfUri.getPath());
            }

            if (fos != null) {
                document.writeTo(fos);
                document.close();
                fos.close();
                Toast.makeText(this, "PDF saved to Downloads!", Toast.LENGTH_LONG).show();

                // Open the PDF file
                openPdf(pdfUri);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    private void openPdf(Uri pdfUri) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Open PDF"));
        } catch (Exception e) {
            Toast.makeText(this, "No PDF viewer installed!", Toast.LENGTH_SHORT).show();
        }
    }
    private void sharePdf() {
        if (pdfUri == null) {
            Toast.makeText(this, "Please create the PDF first.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create share intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Start the share activity
        startActivity(Intent.createChooser(shareIntent, "Share PDF using"));
    }


    private void getAllInfo(){

        SharedPreferences sp = getSharedPreferences("device_details",MODE_PRIVATE);
        SharedPreferences sp2 = getSharedPreferences("saved_login",MODE_PRIVATE);

        String customerName = sp.getString("customerName","");
        String customerMobile = sp.getString("customerMobile","");
        String customerAddress = sp.getString("customerAddress","");
        String posInvoiceNumber = sp.getString("posInvoiceNumber","");
        String downPaymentDate = sp.getString("downPaymentDate","");
        String imei1 = sp.getString("imei1","");
        String barcode = sp.getString("barcode","");
        String serial = sp.getString("serial","");
        String model = sp.getString("model","");
        String brand = sp.getString("brand","");
        String color  = sp.getString("color ","");
        String nextInstallmentDate = sp.getString("nextInstallmentDate","");
        int hireSalePrice = sp.getInt("hireSalePrice",0);
        int numberOfInstallment = sp.getInt("numberOfInstallment",0);
        int downPayment = sp.getInt("downPayment",0);
        int duePayment = hireSalePrice - downPayment;

        int retailerId = sp2.getInt("retailerId",0);
        String retailerName = sp2.getString("retailerName","");

        binding.nameInvoice.setText(customerName);
        binding.mobileInvoice.setText(customerMobile);
        binding.addressInvoice.setText(customerAddress);
        binding.invoiceNo.setText(posInvoiceNumber);
        binding.dateInvoice.setText(downPaymentDate);
        binding.imei1Invoice.setText(imei1);
        binding.imei2Invoice.setText(barcode);
        binding.serialInvoice.setText(serial);
        binding.modelInvoice.setText(model);
        binding.brandInvoice.setText(brand);
        binding.colorInvoice.setText(color);
        binding.hireSaleInvoice.setText(String.valueOf(hireSalePrice));
        binding.installmentInvoice.setText(String.valueOf(numberOfInstallment));
        binding.downPaymentInvoice.setText(String.valueOf(downPayment));
        binding.dueInvoice.setText(String.valueOf(duePayment));
        binding.nextInstallInvoice.setText(nextInstallmentDate);
        binding.retailerIdInvoice.setText(String.valueOf(retailerId));
        binding.retailerNameInvoice.setText(retailerName);
    }

}