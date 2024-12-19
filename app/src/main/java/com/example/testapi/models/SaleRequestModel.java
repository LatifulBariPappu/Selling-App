package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class SaleRequestModel {
    @SerializedName("customer_id") private String customer_id;
    @SerializedName("customer_name") private String customer_name;
    @SerializedName("customer_address") private String customer_address;
    @SerializedName("customer_nid") private String customer_nid;
    @SerializedName("customer_mobile") private String customer_mobile;
    @SerializedName("sales_by") private String sales_by;
    @SerializedName("retail_name") private String retail_name;
    @SerializedName("retail_id") private String retail_id;
    @SerializedName("pos_invoice_number") private String pos_invoice_number;
    @SerializedName("sales_person_name") private String sales_person_name;
    @SerializedName("imei_1") private String imei_1;
    @SerializedName("barcode") private String barcode;
    @SerializedName("brand") private String brand;
    @SerializedName("model") private String model;
    @SerializedName("color") private String color;
    @SerializedName("hire_sale_price") private int hire_sale_price;
    @SerializedName("number_of_installment") private int number_of_installment;
    @SerializedName("down_payment") private int down_payment;
    @SerializedName("down_payment_date") private String down_payment_date;

    public SaleRequestModel(String customer_id, String customer_name, String customer_address, String customer_nid, String customer_mobile, String sales_by, String retail_name, String retail_id, String pos_invoice_number, String sales_person_name, String imei_1, String barcode, String brand, String model, String color, int hire_sale_price, int number_of_installment, int down_payment, String down_payment_date) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_address = customer_address;
        this.customer_nid = customer_nid;
        this.customer_mobile = customer_mobile;
        this.sales_by = sales_by;
        this.retail_name = retail_name;
        this.retail_id = retail_id;
        this.pos_invoice_number = pos_invoice_number;
        this.sales_person_name = sales_person_name;
        this.imei_1 = imei_1;
        this.barcode = barcode;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.hire_sale_price = hire_sale_price;
        this.number_of_installment = number_of_installment;
        this.down_payment = down_payment;
        this.down_payment_date = down_payment_date;
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

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_nid() {
        return customer_nid;
    }

    public void setCustomer_nid(String customer_nid) {
        this.customer_nid = customer_nid;
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

    public String getRetail_name() {
        return retail_name;
    }

    public void setRetail_name(String retail_name) {
        this.retail_name = retail_name;
    }

    public String getRetail_id() {
        return retail_id;
    }

    public void setRetail_id(String retail_id) {
        this.retail_id = retail_id;
    }

    public String getPos_invoice_number() {
        return pos_invoice_number;
    }

    public void setPos_invoice_number(String pos_invoice_number) {
        this.pos_invoice_number = pos_invoice_number;
    }

    public String getSales_person_name() {
        return sales_person_name;
    }

    public void setSales_person_name(String sales_person_name) {
        this.sales_person_name = sales_person_name;
    }

    public String getImei_1() {
        return imei_1;
    }

    public void setImei_1(String imei_1) {
        this.imei_1 = imei_1;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getHire_sale_price() {
        return hire_sale_price;
    }

    public void setHire_sale_price(int hire_sale_price) {
        this.hire_sale_price = hire_sale_price;
    }

    public int getNumber_of_installment() {
        return number_of_installment;
    }

    public void setNumber_of_installment(int number_of_installment) {
        this.number_of_installment = number_of_installment;
    }

    public int getDown_payment() {
        return down_payment;
    }

    public void setDown_payment(int down_payment) {
        this.down_payment = down_payment;
    }

    public String getDown_payment_date() {
        return down_payment_date;
    }

    public void setDown_payment_date(String down_payment_date) {
        this.down_payment_date = down_payment_date;
    }
}
