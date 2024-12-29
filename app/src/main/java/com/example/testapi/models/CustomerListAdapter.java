package com.example.testapi.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.activities.CustomerDetailsActivity;
import com.example.testapi.databinding.SingleCustomerDesignBinding;

import java.util.ArrayList;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder>{
    List<Devices> devicesList;
    List<Devices> fullList;
    private Context context;

    public CustomerListAdapter(Context context,List<Devices> devicesList) {
        this.context = context;
        this.devicesList = devicesList;
        this.fullList = new ArrayList<>(devicesList);
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleCustomerDesignBinding binding = SingleCustomerDesignBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CustomerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Devices devices = devicesList.get(position);
        holder.binding.customerListNameTv.setText("Name : "+devices.getCustomer_name());
        holder.binding.customerListMobileTv.setText("Mobile : "+devices.getCustomer_mobile());
        holder.binding.customerListImei1Tv.setText("IMEI : "+devices.getImei_1());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomerDetailsActivity.class);
                intent.putExtra("imei1",devices.getImei_1());
                intent.putExtra("imei2",devices.getImei_2());
                intent.putExtra("serial",devices.getSerial_number());
                intent.putExtra("color",devices.getColor());
                intent.putExtra("downPayDate",devices.getDown_payment_date());
                intent.putExtra("model",devices.getModel());
                intent.putExtra("brand",devices.getBrand());
                intent.putExtra("name",devices.getCustomer_name());
                intent.putExtra("mobile",devices.getCustomer_mobile());
                intent.putExtra("lastSync",devices.getLast_sync());
                intent.putExtra("name",devices.getCustomer_name());
                intent.putExtra("mobile",devices.getCustomer_mobile());
                intent.putExtra("id",devices.getCustomer_id());
                intent.putExtra("nid",devices.getCustomer_nid());
                intent.putExtra("address",devices.getCustomer_address());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder{
        SingleCustomerDesignBinding binding;
        public CustomerViewHolder(SingleCustomerDesignBinding binding) {
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
                        device.getCustomer_mobile().toLowerCase().contains(query.toLowerCase()) ||
                        device.getImei_1().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(device);
                }
            }
            devicesList.clear();
            devicesList.addAll(filteredList); // Add filtered results
        }
        notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }
}
