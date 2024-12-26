package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class AddDevice {
    @SerializedName("status")
    private String status;
    @SerializedName("statusCode")
    private int statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("role")
    private String role;
    @SerializedName("data")
    private String data;

    public AddDevice(String status, int statusCode, String message, String role, String data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.role = role;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
