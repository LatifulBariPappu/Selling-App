package com.example.testapi.models;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.activities.DeviceDetailsActivity;
import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.DefaulterSingleDesignBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaulterAdapter extends RecyclerView.Adapter<DefaulterAdapter.DefaulterViewHolder> {
    private List<DefaulterResponse.Defaulter> defaulterList;
    List<DefaulterResponse.Defaulter> fullList;
    private Context context;

    public DefaulterAdapter(Context context,List<DefaulterResponse.Defaulter> defaulterList) {

        this.context = context;
        this.defaulterList = defaulterList;
        this.fullList = new ArrayList<>(defaulterList);
    }

    @NonNull
    @Override
    public DefaulterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DefaulterSingleDesignBinding binding = DefaulterSingleDesignBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DefaulterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaulterViewHolder holder, int position) {
        DefaulterResponse.Defaulter defaulter = defaulterList.get(position);
        holder.binding.defaulterNameTv.setText("Name : "+defaulter.getCustomer_name());
        holder.binding.defaulterMobileTv.setText("Mobile : "+defaulter.getCustomer_mobile());
        holder.binding.defaulterImei1Tv.setText("IMEI: " + defaulter.getImei_1());
        holder.binding.defaultedAmountTv.setText("Defaulted Amount: " + defaulter.getTotal_defaulted_amount());
        holder.binding.downPayTv.setText("Down Payment: " + defaulter.getDown_payment_date());
        holder.binding.defaultedDateTv.setText("Defaulted Date: " + defaulter.getDefaulted_date());
        holder.binding.remainingToPayTv.setText("Remaining to pay : "+defaulter.getRemaining_to_pay());

        holder.binding.deviceDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.defaulterProgress.setVisibility(View.VISIBLE);
                holder.binding.deviceDetailBtn.setVisibility(View.GONE);
                SharedPreferences sp = context.getSharedPreferences("defaulters",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("isDefaulter","yes");
                editor.apply();
                Intent intent = new Intent(context, DeviceDetailsActivity.class);
                intent.putExtra("defaulterImei1",defaulter.getImei_1());
                intent.putExtra("defaulterImei2",defaulter.getImei_2());
                intent.putExtra("defaulterName",defaulter.getCustomer_name());
                intent.putExtra("defaulterMobile",defaulter.getCustomer_mobile());
                intent.putExtra("totalDefaultedAmount",defaulter.getTotal_defaulted_amount());
                intent.putExtra("defaulterDownPaymentDate",defaulter.getDown_payment_date());
                intent.putExtra("defaultedDate",defaulter.getDefaulted_date());
                intent.putExtra("remainingToPay",defaulter.getRemaining_to_pay());
                intent.putExtra("nid",defaulter.getCustomer_nid());
                context.startActivity(intent);
            }
        });

        holder.binding.deviceLockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.defaulterLockProgress.setVisibility(View.VISIBLE);
                holder.binding.deviceLockBtn.setVisibility(View.GONE);

                Call<LockDeviceModel> call = ApiController
                        .getInstance()
                        .getapi()
                        .lockDevice(defaulter.getImei_1());

                call.enqueue(new Callback<LockDeviceModel>() {
                    @Override
                    public void onResponse(Call<LockDeviceModel> call, Response<LockDeviceModel> response) {
                        holder.binding.defaulterLockProgress.setVisibility(View.GONE);
                        holder.binding.deviceLockBtn.setVisibility(View.VISIBLE);
                        if(response.isSuccessful() && response.body()!=null){
                            String status = response.body().getStatus();
                            if("Successful".equals(status)){
                                Toast.makeText(context,"Device Locked Successfully",Toast.LENGTH_SHORT).show();
                            } else if ("Unsuccessful".equals(status)) {
                                Toast.makeText(context,"Device Not Found!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LockDeviceModel> call, Throwable t) {
                        holder.binding.defaulterLockProgress.setVisibility(View.GONE);
                        holder.binding.deviceLockBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Failed : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return defaulterList.size();
    }

    public static class DefaulterViewHolder extends RecyclerView.ViewHolder {
        DefaulterSingleDesignBinding binding;

        public DefaulterViewHolder(DefaulterSingleDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        if (query.isEmpty()) {
            defaulterList.clear();
            defaulterList.addAll(fullList); // Restore the full list
        } else {
            List<DefaulterResponse.Defaulter> filteredList = new ArrayList<>();
            for (DefaulterResponse.Defaulter defaulter : fullList) {
                if (defaulter.getCustomer_name().toLowerCase().contains(query.toLowerCase()) ||
                        defaulter.getCustomer_mobile().toLowerCase().contains(query.toLowerCase()) ||
                        defaulter.getImei_1().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(defaulter);
                }
            }
            defaulterList.clear();
            defaulterList.addAll(filteredList); // Add filtered results
        }
        notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }
}