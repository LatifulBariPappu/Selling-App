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
        @SerializedName("next_installment_date")
        private String next_installment_date;
        @SerializedName("message") private String message;

        public Data(String customerId, String imei1, String next_installment_date, String message) {
            this.customerId = customerId;
            this.imei1 = imei1;
            this.next_installment_date = next_installment_date;
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

        public String getNext_installment_date() {
            return next_installment_date;
        }

        public void setNext_installment_date(String next_installment_date) {
            this.next_installment_date = next_installment_date;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
