package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class PaymentModel {
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    Data data;

    public PaymentModel(int status, Data data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        @SerializedName("customer_id")
        private String customer_id;
        @SerializedName("message")
        private String message;

        public Data(String customer_id, String message) {
            this.customer_id = customer_id;
            this.message = message;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
