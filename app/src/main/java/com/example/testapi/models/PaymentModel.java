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
        @SerializedName("imei_1")
        private String imei_1;
        @SerializedName("message")
        private String message;

        public Data(String imei_1, String message) {
            this.imei_1 = imei_1;
            this.message = message;
        }

        public String getImei_1() {
            return imei_1;
        }

        public void setImei_1(String imei_1) {
            this.imei_1 = imei_1;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
