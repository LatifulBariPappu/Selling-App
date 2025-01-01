package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoodCustomerModel {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("defaulters")
    List<defaulters> defaultersList;

    public GoodCustomerModel(String message, int status, List<defaulters> defaultersList) {
        this.message = message;
        this.status = status;
        this.defaultersList = defaultersList;
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

    public List<defaulters> getDefaultersList() {
        return defaultersList;
    }

    public void setDefaultersList(List<defaulters> defaultersList) {
        this.defaultersList = defaultersList;
    }

    public static class defaulters{
        @SerializedName("imei_1")
        private String imei_1;
        @SerializedName("imei_2")
        private String imei_2;
        @SerializedName("serial_number")
        private String serial_number;
        @SerializedName("customer_id")
        private String customer_id;
        @SerializedName("customer_name")
        private String customer_name;
        @SerializedName("customer_nid")
        private long customer_nid;
        @SerializedName("customer_address")
        private String customer_address;
        @SerializedName("customer_mobile")
        private String customer_mobile;
        @SerializedName("down_payment_date")
        private String down_payment_date;

        public defaulters(String imei_1, String imei_2, String serial_number, String customer_id, String customer_name, long customer_nid, String customer_address, String customer_mobile, String down_payment_date) {
            this.imei_1 = imei_1;
            this.imei_2 = imei_2;
            this.serial_number = serial_number;
            this.customer_id = customer_id;
            this.customer_name = customer_name;
            this.customer_nid = customer_nid;
            this.customer_address = customer_address;
            this.customer_mobile = customer_mobile;
            this.down_payment_date = down_payment_date;
        }

        public String getImei_1() {
            return imei_1;
        }

        public void setImei_1(String imei_1) {
            this.imei_1 = imei_1;
        }

        public String getImei_2() {
            return imei_2;
        }

        public void setImei_2(String imei_2) {
            this.imei_2 = imei_2;
        }

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public long getCustomer_nid() {
            return customer_nid;
        }

        public void setCustomer_nid(long customer_nid) {
            this.customer_nid = customer_nid;
        }

        public String getCustomer_address() {
            return customer_address;
        }

        public void setCustomer_address(String customer_address) {
            this.customer_address = customer_address;
        }

        public String getCustomer_mobile() {
            return customer_mobile;
        }

        public void setCustomer_mobile(String customer_mobile) {
            this.customer_mobile = customer_mobile;
        }

        public String getDown_payment_date() {
            return down_payment_date;
        }

        public void setDown_payment_date(String down_payment_date) {
            this.down_payment_date = down_payment_date;
        }
    }
}
