package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BalanceModel {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private BalanceData data; // For success response

    @SerializedName("errors")
    private Errors errors; // For validation error response

    // Getters and Setters
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

    public BalanceData getData() {
        return data;
    }

    public void setData(BalanceData data) {
        this.data = data;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    // BalanceData class
    public static class BalanceData {

        @SerializedName("purchased_subscription")
        private int purchasedSubscription;

        @SerializedName("consumed_subscription")
        private int consumedSubscription;

        @SerializedName("balance")
        private int balance;

        // Getters and Setters
        public int getPurchasedSubscription() {
            return purchasedSubscription;
        }

        public void setPurchasedSubscription(int purchasedSubscription) {
            this.purchasedSubscription = purchasedSubscription;
        }

        public int getConsumedSubscription() {
            return consumedSubscription;
        }

        public void setConsumedSubscription(int consumedSubscription) {
            this.consumedSubscription = consumedSubscription;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }

    // Errors class for validation error
    public static class Errors {

        @SerializedName("retail_id")
        private List<String> retailId;

        // Getter and Setter
        public List<String> getRetailId() {
            return retailId;
        }

        public void setRetailId(List<String> retailId) {
            this.retailId = retailId;
        }
    }
}
