package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class SaleResponseModel {
    @SerializedName("status") private int status;
    @SerializedName("data") Data dataObject;

    public SaleResponseModel(int status, Data dataObject) {
        this.status = status;
        this.dataObject = dataObject;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data getDataObject() {
        return dataObject;
    }

    public void setDataObject(Data dataObject) {
        this.dataObject = dataObject;
    }

    public static class Data {
        @SerializedName("customer_id") private String customerId;
        @SerializedName("imei_1") private String imei1;
        @SerializedName("message") private String message;

        public Data(String customerId, String imei1, String message) {
            this.customerId = customerId;
            this.imei1 = imei1;
            this.message = message;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getImei1() {
            return imei1;
        }

        public void setImei1(String imei1) {
            this.imei1 = imei1;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
