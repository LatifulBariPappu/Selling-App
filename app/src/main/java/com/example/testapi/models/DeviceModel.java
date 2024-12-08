package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class DeviceModel {
    @SerializedName("message")
    private String message;
    @SerializedName("device_models")
    Device_models Device_modelsObject;
    @SerializedName("status")
    private int status;



    // Getter Methods

    public String getMessage() {
        return message;
    }

    public Device_models getDevice_models() {
        return Device_modelsObject;
    }

    public int getStatus() {
        return status;
    }

    // Setter Methods

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDevice_models(Device_models device_modelsObject) {
        this.Device_modelsObject = device_modelsObject;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
