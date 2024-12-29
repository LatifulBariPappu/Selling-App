package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryModel {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    List<data> data;

    public HistoryModel(String message, int status, List<HistoryModel.data> data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

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

    public List<HistoryModel.data> getData() {
        return data;
    }

    public void setData(List<HistoryModel.data> data) {
        this.data = data;
    }

    public static class data{
        @SerializedName("imei_1")
        private String imei_1;
        @SerializedName("payment_date")
        private String payment_date;
        @SerializedName("payment_amount")
        private int payment_amount;

        public data(String imei_1, String payment_date, int payment_amount) {
            this.imei_1 = imei_1;
            this.payment_date = payment_date;
            this.payment_amount = payment_amount;
        }

        public String getImei_1() {
            return imei_1;
        }

        public void setImei_1(String imei_1) {
            this.imei_1 = imei_1;
        }

        public String getPayment_date() {
            return payment_date;
        }

        public void setPayment_date(String payment_date) {
            this.payment_date = payment_date;
        }

        public int getPayment_amount() {
            return payment_amount;
        }

        public void setPayment_amount(int payment_amount) {
            this.payment_amount = payment_amount;
        }
    }
}
