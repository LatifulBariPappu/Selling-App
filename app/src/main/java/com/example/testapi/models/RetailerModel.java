package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class RetailerModel {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("retail")
    Retail retailObject;

    public RetailerModel(String message, int status, Retail retailObject) {
        this.message = message;
        this.status = status;
        this.retailObject = retailObject;
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

    public Retail getRetailObject() {
        return retailObject;
    }

    public void setRetailObject(Retail retailObject) {
        this.retailObject = retailObject;
    }

    public static class Retail{
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("address")
        private String address;
        @SerializedName("dc_id")
        private String dc_id;
        @SerializedName("distributor_name")
        private String distributor_name;

        public Retail(int id, String name, String mobile, String address, String dc_id, String distributor_name) {
            this.id = id;
            this.name = name;
            this.mobile = mobile;
            this.address = address;
            this.dc_id = dc_id;
            this.distributor_name = distributor_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDc_id() {
            return dc_id;
        }

        public void setDc_id(String dc_id) {
            this.dc_id = dc_id;
        }

        public String getDistributor_name() {
            return distributor_name;
        }

        public void setDistributor_name(String distributor_name) {
            this.distributor_name = distributor_name;
        }
    }

}
