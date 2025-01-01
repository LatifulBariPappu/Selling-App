package com.example.testapi.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.activities.CustomerDetailsActivity;
import com.example.testapi.databinding.SingleCustomerDesignBinding;

import java.util.ArrayList;
import java.util.List;

public class GoodCustomerAdapter extends RecyclerView.Adapter<GoodCustomerAdapter.GoodCustomerViewHolder>{
    private Context context;
    List<GoodCustomerModel.defaulters> defaultersList;
    List<GoodCustomerModel.defaulters> fullList;


    public GoodCustomerAdapter(Context context, List<GoodCustomerModel.defaulters> defaultersList) {
        this.context = context;
        this.defaultersList = defaultersList;
        this.fullList = new ArrayList<>(defaultersList);
    }

    @NonNull
    @Override
    public GoodCustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleCustomerDesignBinding binding = SingleCustomerDesignBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new GoodCustomerViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull GoodCustomerViewHolder holder, int position) {
        GoodCustomerModel.defaulters devices = defaultersList.get(position);
        holder.binding.customerListIV.setImageResource(R.drawable.goodcustomer);
        holder.binding.customerListNameTv.setText("Name : "+devices.getCustomer_name());
        holder.binding.customerListMobileTv.setText("Mobile : "+devices.getCustomer_mobile());
        holder.binding.customerListImei1Tv.setText("IMEI : "+devices.getImei_1());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = context.getSharedPreferences("customers",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("type","good");
                editor.apply();
                Intent intent = new Intent(context, CustomerDetailsActivity.class);
                intent.putExtra("imei1",devices.getImei_1());
                intent.putExtra("imei2",devices.getImei_2());
                intent.putExtra("serial",devices.getSerial_number());
                intent.putExtra("downPayDate",devices.getDown_payment_date());
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
        return defaultersList.size();
    }

    static class GoodCustomerViewHolder extends RecyclerView.ViewHolder {
        SingleCustomerDesignBinding binding;
        public GoodCustomerViewHolder(SingleCustomerDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        if (query.isEmpty()) {
            defaultersList.clear();
            defaultersList.addAll(fullList); // Restore the full list
        } else {
            List<GoodCustomerModel.defaulters> filteredList = new ArrayList<>();
            for (GoodCustomerModel.defaulters device : fullList) {
                if (device.getCustomer_name().toLowerCase().contains(query.toLowerCase()) ||
                        device.getCustomer_mobile().toLowerCase().contains(query.toLowerCase()) ||
                        device.getImei_1().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(device);
                }
            }
            defaultersList.clear();
            defaultersList.addAll(filteredList); // Add filtered results
        }
        notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }
}
