package com.example.testapi.models;
public class DeviceModel {
    private String message;
    DeviceDetails Device_modelsObject;
    private float status;


    // Getter Methods

    public String getMessage() {
        return message;
    }

    public DeviceDetails getDevice_models() {
        return Device_modelsObject;
    }

    public float getStatus() {
        return status;
    }

    // Setter Methods

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDevice_models(DeviceDetails device_modelsObject) {
        this.Device_modelsObject = device_modelsObject;
    }

    public void setStatus(float status) {
        this.status = status;
    }
}
