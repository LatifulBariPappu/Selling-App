package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class DefaulterStatus {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("total_defaulters")
    private int total_defaulters;
    @SerializedName("total_defaulted_amount")
    private int total_defaulted_amount;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal_defaulters() {
        return total_defaulters;
    }

    public void setTotal_defaulters(int total_defaulters) {
        this.total_defaulters = total_defaulters;
    }

    public int getTotal_defaulted_amount() {
        return total_defaulted_amount;
    }

    public void setTotal_defaulted_amount(int total_defaulted_amount) {
        this.total_defaulted_amount = total_defaulted_amount;
    }
}
