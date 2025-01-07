package com.example.testapi.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.activities.DeviceDetailsActivity;
import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.SingleDeviceDesignBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceAdapter  extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>{

    List<Devices> devicesList;
    List<Devices> fullList;
    private Context context;

    public DeviceAdapter(Context context,List<Devices> devicesList) {
        this.context = context;
        this.devicesList = devicesList;
        this.fullList = new ArrayList<>(devicesList);
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleDeviceDesignBinding binding = SingleDeviceDesignBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DeviceViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Devices devices = devicesList.get(position);
        holder.binding.nameTv.setText("Name : "+devices.getCustomer_name());
        holder.binding.mobileTv.setText("Mobile : "+devices.getCustomer_mobile());
        holder.binding.imei1Tv.setText("IMEI1 : "+devices.getImei_1());
        holder.binding.imei2Tv.setText("IMEI2 : "+devices.getImei_2());
        holder.binding.sellDateTv.setText("Sell date : "+devices.getDown_payment_date());
        holder.binding.lastSyncTv.setText("Last sync : "+devices.getLast_sync());
        int emi_status = devices.getEmi_status();
        int enroll_status = devices.getAcknowledgment();
        if (emi_status==1){
            holder.binding.deviceEmiStatusTV.setText("Running");
        }else if(emi_status==0){
            holder.binding.deviceEmiStatusTV.setText("Completed");
            holder.binding.deviceEmiStatusTV.setBackgroundResource(R.color.custom_blue);
            holder.binding.deviceEmiStatusTV.setTextColor(Color.WHITE);
        }
        if(enroll_status==1){
            holder.binding.deviceEnrollStatusTV.setText("Enroll");
        } else if (enroll_status==0) {
            holder.binding.deviceEnrollStatusTV.setText("NotEnroll");
            holder.binding.deviceEnrollStatusTV.setBackgroundResource(R.color.custom_red);
            holder.binding.deviceEnrollStatusTV.setTextColor(Color.WHITE);
        }

        holder.binding.deviceDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.deviceDetailProgress.setVisibility(View.VISIBLE);
                holder.binding.deviceDetailBtn.setVisibility(View.GONE);
                SharedPreferences sp = context.getSharedPreferences("defaulters",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("isDefaulter","no");
                editor.apply();
                Intent intent = new Intent(context, DeviceDetailsActivity.class);
                intent.putExtra("imei1",devices.getImei_1());
                intent.putExtra("imei2",devices.getImei_2());
                intent.putExtra("downPayDate",devices.getDown_payment_date());
                intent.putExtra("model",devices.getModel());
                intent.putExtra("name",devices.getCustomer_name());
                intent.putExtra("mobile",devices.getCustomer_mobile());
                intent.putExtra("nid",devices.getCustomer_nid());
                intent.putExtra("totalPayment",devices.getTotal_payment());
                intent.putExtra("totalDue",devices.getTotal_due());
                intent.putExtra("lastSync",devices.getLast_sync());
                intent.putExtra("deviceStatus",devices.getDevice_status());
                intent.putExtra("nextPayDate",devices.getNext_payment_date());
                intent.putExtra("nextPayAmount",devices.getNext_payment_amount());
                context.startActivity(intent);
            }
        });

        holder.binding.deviceLockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.deviceLockProgress.setVisibility(View.VISIBLE);
                holder.binding.deviceLockBtn.setVisibility(View.GONE);

                Call<LockDeviceModel> call = ApiController
                        .getInstance()
                        .getapi()
                        .lockDevice(devices.getImei_1());

                call.enqueue(new Callback<LockDeviceModel>() {
                    @Override
                    public void onResponse(Call<LockDeviceModel> call, Response<LockDeviceModel> response) {
                        holder.binding.deviceLockProgress.setVisibility(View.GONE);
                        holder.binding.deviceLockBtn.setVisibility(View.VISIBLE);

                        if (response.isSuccessful() && response.body() != null) {
                            LockDeviceModel responseBody = response.body();
                            int statusCode = responseBody.getStatusCode();
                            String message = responseBody.getMessage();

                            switch (statusCode) {
                                case 200: // Success
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    break;
                                case 404: // Not Found or Unsuccessful
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    break;
                                case 500: // Error Occurred
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    break;
                                default: // Unknown Response
                                    Toast.makeText(context, "Unexpected response: " + message, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            Toast.makeText(context, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LockDeviceModel> call, Throwable t) {
                        holder.binding.deviceLockProgress.setVisibility(View.GONE);
                        holder.binding.deviceLockBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder{
        SingleDeviceDesignBinding binding;
        public DeviceViewHolder(SingleDeviceDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        if (query.isEmpty()) {
            devicesList.clear();
            devicesList.addAll(fullList); // Restore the full list
        } else {
            List<Devices> filteredList = new ArrayList<>();
            for (Devices device : fullList) {
                if (device.getCustomer_name().toLowerCase().contains(query.toLowerCase()) ||
                        device.getModel().toLowerCase().contains(query.toLowerCase()) ||
                        device.getCustomer_mobile().toLowerCase().contains(query.toLowerCase()) ||
                        device.getImei_1().toLowerCase().contains(query.toLowerCase()) ||
                        device.getImei_2().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(device);
                }
            }
            devicesList.clear();
            devicesList.addAll(filteredList); // Add filtered results
        }
        notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }

}
