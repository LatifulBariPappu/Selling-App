package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class Device_models {
    @SerializedName("imei_1")
    private String imei_1;
    @SerializedName("imei_2")
    private String imei_2;
    @SerializedName("model")
    private String device;
    @SerializedName("serial_number")
    private String serialNumber;
    @SerializedName("brand")
    private String brand;
    @SerializedName("color")
    private String color;
    @SerializedName("hire_sale_price")
    private String price;

    public Device_models(String imei_1, String imei_2, String device, String serialNumber, String brand, String color, String price) {
        this.imei_1 = imei_1;
        this.imei_2 = imei_2;
        this.device = device;
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.color = color;
        this.price = price;
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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
