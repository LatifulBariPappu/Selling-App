package com.example.testapi.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.databinding.SingleHistoryItemBinding;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    List<HistoryModel.data> historyList;
    public HistoryAdapter(List<HistoryModel.data> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleHistoryItemBinding binding = SingleHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryModel.data history = historyList.get(position);
        holder.binding.historySlNoTv.setText(String.valueOf(position+1));
        holder.binding.historyAmountTv.setText(String.valueOf(history.getPayment_amount()));
        holder.binding.historyDateTv.setText(history.getPayment_date());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {

        SingleHistoryItemBinding binding;
        public HistoryViewHolder(SingleHistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
