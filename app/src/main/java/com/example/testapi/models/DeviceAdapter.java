package com.example.testapi.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.activities.DeviceDetailsActivity;
import com.example.testapi.databinding.SingleDeviceDesignBinding;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter  extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>{

    List<Devices> devicesList;
    List<Devices> fullList;
    private Context context;
    private boolean showDefaulterText;

    public DeviceAdapter(Context context,List<Devices> devicesList) {
        this.context = context;
        this.devicesList = devicesList;
        this.fullList = new ArrayList<>(devicesList);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setShowDefaulterText(boolean showDefaulterText) {
        this.showDefaulterText = showDefaulterText;
        notifyDataSetChanged();
    }
    public boolean isShowDefaulterText() {
        return showDefaulterText;
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
        holder.binding.nameTv.setText(devices.getCustomer_name());
        holder.binding.mobileTv.setText("Mobile : "+devices.getCustomer_mobile());
        holder.binding.imei1Tv.setText("IMEI1 : "+devices.getImei_1());
        holder.binding.imei2Tv.setText("IMEI2 : "+devices.getImei_2());
        holder.binding.sellDateTv.setText("Sell date : "+devices.getDown_payment_date());
        holder.binding.lastSyncTv.setText("Last sync : "+devices.getLast_sync());

        if(showDefaulterText){
            holder.binding.defaulterAmountTv.setVisibility(View.VISIBLE);
            holder.binding.lastPaymentTv.setVisibility(View.VISIBLE);
            holder.binding.defaulterAmountTv.setText("Defaulter amount : "+String.valueOf(devices.getDefaulted_amount()));
            holder.binding.lastPaymentTv.setText("Last pay date : "+devices.getLast_payment_date());
        }else{
            holder.binding.defaulterAmountTv.setVisibility(View.GONE);
            holder.binding.lastPaymentTv.setVisibility(View.GONE);
        }

        holder.binding.deviceDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeviceDetailsActivity.class);
                intent.putExtra("imei1",devices.getImei_1());
                intent.putExtra("imei2",devices.getImei_2());
                intent.putExtra("downPayDate",devices.getDown_payment_date());
                intent.putExtra("model",devices.getModel());
                intent.putExtra("name",devices.getCustomer_name());
                intent.putExtra("mobile",devices.getCustomer_mobile());
                intent.putExtra("lastSync",devices.getLast_sync());
                context.startActivity(intent);
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
