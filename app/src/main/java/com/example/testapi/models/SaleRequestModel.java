package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class SaleRequestModel {
    @SerializedName("customer_id") private String customerId;
    @SerializedName("customer_name") private String customerName;
    @SerializedName("customer_address") private String customerAddress;
    @SerializedName("customer_nid") private int customerNid;
    @SerializedName("customer_mobile") private String customerMobile;
    @SerializedName("sales_by") private String salesBy;
    @SerializedName("plaza_name") private String plazaName;
    @SerializedName("plaza_id") private String plazaId;
    @SerializedName("pos_invoice_number") private String posInvoiceNumber;
    @SerializedName("sales_person_name") private String salesPersonName;
    @SerializedName("imei_1") private String imei1;
    @SerializedName("barcode") private String barcode;
    @SerializedName("brand") private String brand;
    @SerializedName("model") private String model;
    @SerializedName("color") private String color;
    @SerializedName("hire_sale_price") private int hireSalePrice;
    @SerializedName("number_of_installment") private int numberOfInstallment;
    @SerializedName("down_payment") private int downPayment;
    @SerializedName("down_payment_date") private String downPaymentDate;

    public SaleRequestModel(String customerId, String customerName, String customerAddress, int customerNid, String customerMobile, String salesBy, String plazaName, String plazaId, String posInvoiceNumber, String salesPersonName, String imei1, String barcode, String brand, String model, String color, int hireSalePrice, int numberOfInstallment, int downPayment, String downPaymentDate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerNid = customerNid;
        this.customerMobile = customerMobile;
        this.salesBy = salesBy;
        this.plazaName = plazaName;
        this.plazaId = plazaId;
        this.posInvoiceNumber = posInvoiceNumber;
        this.salesPersonName = salesPersonName;
        this.imei1 = imei1;
        this.barcode = barcode;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.hireSalePrice = hireSalePrice;
        this.numberOfInstallment = numberOfInstallment;
        this.downPayment = downPayment;
        this.downPaymentDate = downPaymentDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getCustomerNid() {
        return customerNid;
    }

    public void setCustomerNid(int customerNid) {
        this.customerNid = customerNid;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getSalesBy() {
        return salesBy;
    }

    public void setSalesBy(String salesBy) {
        this.salesBy = salesBy;
    }

    public String getPlazaName() {
        return plazaName;
    }

    public void setPlazaName(String plazaName) {
        this.plazaName = plazaName;
    }

    public String getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(String plazaId) {
        this.plazaId = plazaId;
    }

    public String getPosInvoiceNumber() {
        return posInvoiceNumber;
    }

    public void setPosInvoiceNumber(String posInvoiceNumber) {
        this.posInvoiceNumber = posInvoiceNumber;
    }

    public String getSalesPersonName() {
        return salesPersonName;
    }

    public void setSalesPersonName(String salesPersonName) {
        this.salesPersonName = salesPersonName;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
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

    public int getHireSalePrice() {
        return hireSalePrice;
    }

    public void setHireSalePrice(int hireSalePrice) {
        this.hireSalePrice = hireSalePrice;
    }

    public int getNumberOfInstallment() {
        return numberOfInstallment;
    }

    public void setNumberOfInstallment(int numberOfInstallment) {
        this.numberOfInstallment = numberOfInstallment;
    }

    public int getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(int downPayment) {
        this.downPayment = downPayment;
    }

    public String getDownPaymentDate() {
        return downPaymentDate;
    }

    public void setDownPaymentDate(String downPaymentDate) {
        this.downPaymentDate = downPaymentDate;
    }
}
