package com.example.testapi.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.databinding.SingleTodayDefaultersBinding;

import java.util.List;

public class TodayDefaulterAdapter extends RecyclerView.Adapter<TodayDefaulterAdapter.TodayDefaulterViewHolder>{

    List<DefaulterStatus.TodayDefaulters> todayDefaultersList;
    public TodayDefaulterAdapter(List<DefaulterStatus.TodayDefaulters> todayDefaultersList) {
        this.todayDefaultersList = todayDefaultersList;
    }

    @NonNull
    @Override
    public TodayDefaulterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleTodayDefaultersBinding binding = SingleTodayDefaultersBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new TodayDefaulterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayDefaulterViewHolder holder, int position) {
        DefaulterStatus.TodayDefaulters todayDefaulters = todayDefaultersList.get(position);
        holder.binding.todayDefaulterNameTv.setText("Name : "+todayDefaulters.getCustomer_name());
        holder.binding.todayDefaulterMobileTv.setText("Mobile : "+todayDefaulters.getCustomer_mobile());
        holder.binding.todayDefaulterImei1Tv.setText("IMEI : "+todayDefaulters.getImei_1());
        holder.binding.todayDefaulterAmountTv.setText("Defaulted amount :"+todayDefaulters.getTotal_defaulted_amount()+" BDT");
        holder.binding.todayDefaulterDueTv.setText("Total Due : "+todayDefaulters.getRemaining_to_pay()+" BDT");
    }

    @Override
    public int getItemCount() {
        return todayDefaultersList.size();
    }

    static class TodayDefaulterViewHolder extends RecyclerView.ViewHolder {
        SingleTodayDefaultersBinding binding;
        public TodayDefaulterViewHolder(SingleTodayDefaultersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
