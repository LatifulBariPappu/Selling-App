package com.example.testapi.models;

public class DeviceDetails {
    private String device;
    private String brand;
    private String color;
    private String price;
    private String imei_1;
    private String imei_2;

    public DeviceDetails(String device, String brand, String color, String price, String imei_1, String imei_2) {
        this.device = device;
        this.brand = brand;
        this.color = color;
        this.price = price;
        this.imei_1 = imei_1;
        this.imei_2 = imei_2;
    }
    // Getter Methods

    public String getDevice() {
        return device;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getPrice() {
        return price;
    }

    public String getImei_1() {
        return imei_1;
    }

    public String getImei_2() {
        return imei_2;
    }

    // Setter Methods 

    public void setDevice(String device) {
        this.device = device;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImei_1(String imei_1) {
        this.imei_1 = imei_1;
    }

    public void setImei_2(String imei_2) {
        this.imei_2 = imei_2;
    }
}
