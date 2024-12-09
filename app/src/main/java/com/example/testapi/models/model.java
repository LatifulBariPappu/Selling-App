package com.example.testapi.models;


import com.google.gson.annotations.SerializedName;

public class model {

    @SerializedName("message")
    private String message;
    @SerializedName("distributor")
    Distributor distributorObject;

    public model(String message, Distributor distributorObject) {
        this.message = message;
        this.distributorObject = distributorObject;
    }
    // Getter Methods

    public String getMessage() {
        return message;
    }

    public Distributor getDistributor() {
        return distributorObject;
    }

    // Setter Methods

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDistributor(Distributor distributorObject) {
        this.distributorObject = distributorObject;
    }
}
class Distributor {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("address")
    private String address;
    @SerializedName("no_of_subscription")
    private int no_of_subscription;
    @SerializedName("status")
    private int status;

    public Distributor(int id, String name, String mobile, String address, int no_of_subscription, int status) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.no_of_subscription = no_of_subscription;
        this.status = status;
    }
    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public float getNo_of_subscription() {
        return no_of_subscription;
    }

    public float getStatus() {
        return status;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNo_of_subscription(int no_of_subscription) {
        this.no_of_subscription = no_of_subscription;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
