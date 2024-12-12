package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public  class Devices{
    @SerializedName("imei_1")
    private String imei_1;
    @SerializedName("imei_2")
    private String imei_2;
    @SerializedName("serial_number")
    private String serial_number;
    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("customer_nid")
    private int customer_nid;
    @SerializedName("customer_address")
    private String customer_address;
    @SerializedName("customer_mobile")
    private String customer_mobile;
    @SerializedName("sales_by")
    private String sales_by;
    @SerializedName("sales_person_name")
    private String sales_person_name;
    @SerializedName("plaza_id")
    private String plaza_id;
    @SerializedName("plaza_name")
    private String plaza_name;
    @SerializedName("pos_invoice_number")
    private String pos_invoice_number;
    @SerializedName("model")
    private String model;
    @SerializedName("brand")
    private String brand;
    @SerializedName("color")
    private String color;
    @SerializedName("distributor_id")
    private int distributor_id;
    @SerializedName("distributor_name")
    private String distributor_name;
    @SerializedName("hire_sale_price")
    private int hire_sale_price;
    @SerializedName("sell_status")
    private int sell_status;
    @SerializedName("last_sync")
    private String last_sync;
    @SerializedName("down_payment_date")
    private String down_payment_date;

    public Devices(String imei_1, String imei_2, String serial_number, String customer_id, String customer_name, int customer_nid, String customer_address, String customer_mobile, String sales_by, String sales_person_name, String plaza_id, String plaza_name, String pos_invoice_number, String model, String brand, String color, int distributor_id, String distributor_name, int hire_sale_price, int sell_status, String last_sync, String down_payment_date) {
        this.imei_1 = imei_1;
        this.imei_2 = imei_2;
        this.serial_number = serial_number;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_nid = customer_nid;
        this.customer_address = customer_address;
        this.customer_mobile = customer_mobile;
        this.sales_by = sales_by;
        this.sales_person_name = sales_person_name;
        this.plaza_id = plaza_id;
        this.plaza_name = plaza_name;
        this.pos_invoice_number = pos_invoice_number;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.distributor_id = distributor_id;
        this.distributor_name = distributor_name;
        this.hire_sale_price = hire_sale_price;
        this.sell_status = sell_status;
        this.last_sync = last_sync;
        this.down_payment_date = down_payment_date;
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

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getCustomer_nid() {
        return customer_nid;
    }

    public void setCustomer_nid(int customer_nid) {
        this.customer_nid = customer_nid;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getSales_by() {
        return sales_by;
    }

    public void setSales_by(String sales_by) {
        this.sales_by = sales_by;
    }

    public String getSales_person_name() {
        return sales_person_name;
    }

    public void setSales_person_name(String sales_person_name) {
        this.sales_person_name = sales_person_name;
    }

    public String getPlaza_id() {
        return plaza_id;
    }

    public void setPlaza_id(String plaza_id) {
        this.plaza_id = plaza_id;
    }

    public String getPlaza_name() {
        return plaza_name;
    }

    public void setPlaza_name(String plaza_name) {
        this.plaza_name = plaza_name;
    }

    public String getPos_invoice_number() {
        return pos_invoice_number;
    }

    public void setPos_invoice_number(String pos_invoice_number) {
        this.pos_invoice_number = pos_invoice_number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public int getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(int distributor_id) {
        this.distributor_id = distributor_id;
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

    public String getLast_sync() {
        return last_sync;
    }

    public void setLast_sync(String last_sync) {
        this.last_sync = last_sync;
    }

    public String getDown_payment_date() {
        return down_payment_date;
    }

    public void setDown_payment_date(String down_payment_date) {
        this.down_payment_date = down_payment_date;
    }
}