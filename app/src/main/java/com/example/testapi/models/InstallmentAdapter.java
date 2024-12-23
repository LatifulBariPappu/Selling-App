package com.example.testapi.models;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.databinding.SingleInstallmentItemBinding;

import java.util.List;

public class InstallmentAdapter extends RecyclerView.Adapter<InstallmentAdapter.InstallmentViewHolder>{

    List<EmiScheduleModel.EmiSchedule> emiScheduleList;
    public InstallmentAdapter(List<EmiScheduleModel.EmiSchedule> emiScheduleList) {
        this.emiScheduleList = emiScheduleList;
    }

    @NonNull
    @Override
    public InstallmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleInstallmentItemBinding binding = SingleInstallmentItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new InstallmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InstallmentViewHolder holder, int position) {
        EmiScheduleModel.EmiSchedule emiSchedule = emiScheduleList.get(position);
        holder.binding.installmentNumberTv.setText("Installment Number : "+emiSchedule.getInstallment_number());
        holder.binding.installmentImeiTv.setText("IMEI : "+emiSchedule.getImei_1());
        holder.binding.installmentAmount.setText("Installment amount : "+emiSchedule.getInstallment_amount());
        holder.binding.installmentDate.setText("Installment date : "+emiSchedule.getInstallment_date());
    }

    @Override
    public int getItemCount() {
        return emiScheduleList.size();
    }

    static class InstallmentViewHolder extends RecyclerView.ViewHolder {
    SingleInstallmentItemBinding binding;
    public InstallmentViewHolder(SingleInstallmentItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
}
