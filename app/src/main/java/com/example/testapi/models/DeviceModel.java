package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class DeviceModel {
    @SerializedName("message")
    private String message;
    @SerializedName("device")
    device device;
    @SerializedName("status")
    private int status;

    public DeviceModel(String message, DeviceModel.device device, int status) {
        this.message = message;
        this.device = device;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public device getDevice() {
        return device;
    }

    public void setDevice_models(device device_models) {
        this.device = device_models;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class device {
        @SerializedName("imei_1")
        private String imei_1;
        @SerializedName("imei_2")
        private String imei_2;
        @SerializedName("model")
        private String device;
        @SerializedName("serial_number")
        private String serial_number;
        @SerializedName("brand")
        private String brand;
        @SerializedName("color")
        private String color;
        @SerializedName("distributor_name")
        private String distributor_name;
        @SerializedName("hire_sale_price")
        private int hire_sale_price;
        @SerializedName("sell_status")
        private int sell_status;

        public device(String imei_1, String imei_2, String device, String serial_number, String brand, String color, String distributor_name, int hire_sale_price, int sell_status) {
            this.imei_1 = imei_1;
            this.imei_2 = imei_2;
            this.device = device;
            this.serial_number = serial_number;
            this.brand = brand;
            this.color = color;
            this.distributor_name = distributor_name;
            this.hire_sale_price = hire_sale_price;
            this.sell_status = sell_status;
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

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
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

        public String getDistributor_name() {
            return distributor_name;
        }

        public void setDistributor_name(String distributor_name) {
            this.distributor_name = distributor_name;
        }

        public int getHire_sale_price() {
            return hire_sale_price;
        }

        public void setHire_sale_price(int hire_sale_price) {
            this.hire_sale_price = hire_sale_price;
        }

        public int getSell_status() {
            return sell_status;
        }

        public void setSell_status(int sell_status) {
            this.sell_status = sell_status;
        }
    }

}
