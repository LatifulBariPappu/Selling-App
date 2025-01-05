package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.List;

public class PolicyResponseModel {
    @SerializedName("status")
    private String status;
    @SerializedName("statusCode")
    private int statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("role")
    private String role;
    @SerializedName("data")
    private Object data; // Can hold either a String or a Map for validation errors.

    public PolicyResponseModel(String status, int statusCode, String message, String role, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // Helper method to check if the data is a validation error map
    public Map<String, List<String>> getValidationErrors() {
        if (data instanceof Map) {
            return (Map<String, List<String>>) data;
        }
        return null;
    }

    // Helper method to get data as a String
    public String getDataAsString() {
        if (data instanceof String) {
            return (String) data;
        }
        return null;
    }
}
