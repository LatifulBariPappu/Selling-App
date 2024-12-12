package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceListModel {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("device_list")
    List<Devices> devicesList;

    public DeviceListModel(String message, int status, List<Devices> devicesList) {
        this.message = message;
        this.status = status;
        this.devicesList = devicesList;
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

    public List<Devices> getDevicesList() {
        return devicesList;
    }

    public void setDevicesList(List<Devices> devicesList) {
        this.devicesList = devicesList;
    }


}
