package com.example.testapi.models;


import com.google.gson.annotations.SerializedName;

public class DistributorModel {
    //distributor model class
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("distributor")
    Distributor distributorObject;

    public DistributorModel(String message, int status, Distributor distributorObject) {
        this.message = message;
        this.status = status;
        this.distributorObject = distributorObject;
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

    public Distributor getDistributorObject() {
        return distributorObject;
    }

    public void setDistributorObject(Distributor distributorObject) {
        this.distributorObject = distributorObject;
    }

    public static class Distributor {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("email")
        private String email;
        @SerializedName("address")
        private String address;
        @SerializedName("no_of_subscription")
        private int no_of_subscription;

        public Distributor(int id, String name, String mobile, String email, String address, int no_of_subscription) {
            this.id = id;
            this.name = name;
            this.mobile = mobile;
            this.email = email;
            this.address = address;
            this.no_of_subscription = no_of_subscription;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getNo_of_subscription() {
            return no_of_subscription;
        }

        public void setNo_of_subscription(int no_of_subscription) {
            this.no_of_subscription = no_of_subscription;
        }
    }

}
