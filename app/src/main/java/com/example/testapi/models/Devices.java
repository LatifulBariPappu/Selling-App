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
    private long customer_nid;
    @SerializedName("customer_address")
    private String customer_address;
    @SerializedName("customer_mobile")
    private String customer_mobile;
    @SerializedName("sales_by")
    private String sales_by;
    @SerializedName("sales_person_name")
    private String sales_person_name;
    @SerializedName("retail_id")
    private String retail_id;
    @SerializedName("retail_name")
    private String retail_name;
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
    @SerializedName("number_of_installment")
    private int number_of_installment;
    @SerializedName("down_payment")
    private int down_payment;
    @SerializedName("total_payment")
    private int total_payment;
    @SerializedName("total_due")
    private int total_due;
    @SerializedName("emi_status")
    private int emi_status;
    @SerializedName("acknowledgment")
    private int acknowledgment;
    @SerializedName("device_status")
    private String device_status;
    @SerializedName("device_lock")
    private int device_lock;
    @SerializedName("lock_status")
    private String lock_status;
    @SerializedName("sale_status")
    private int sale_status;
    @SerializedName("last_sync")
    private String last_sync;
    @SerializedName("down_payment_date")
    private String down_payment_date;
    @SerializedName("next_payment_date")
    private String next_payment_date;
    @SerializedName("next_payment_amount")
    private String next_payment_amount;
    @SerializedName("installment_complete")
    private int installment_complete;

    public Devices(String imei_1, String imei_2, String serial_number, String customer_id, String customer_name, long customer_nid, String customer_address, String customer_mobile, String sales_by, String sales_person_name, String retail_id, String retail_name, String pos_invoice_number, String model, String brand, String color, int distributor_id, String distributor_name, int hire_sale_price, int number_of_installment, int down_payment, int total_payment, int total_due, int emi_status, int acknowledgment, String device_status, int device_lock, String lock_status, int sale_status, String last_sync, String down_payment_date, String next_payment_date, String next_payment_amount, int installment_complete) {
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
        this.retail_id = retail_id;
        this.retail_name = retail_name;
        this.pos_invoice_number = pos_invoice_number;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.distributor_id = distributor_id;
        this.distributor_name = distributor_name;
        this.hire_sale_price = hire_sale_price;
        this.number_of_installment = number_of_installment;
        this.down_payment = down_payment;
        this.total_payment = total_payment;
        this.total_due = total_due;
        this.emi_status = emi_status;
        this.acknowledgment = acknowledgment;
        this.device_status = device_status;
        this.device_lock = device_lock;
        this.lock_status = lock_status;
        this.sale_status = sale_status;
        this.last_sync = last_sync;
        this.down_payment_date = down_payment_date;
        this.next_payment_date = next_payment_date;
        this.next_payment_amount = next_payment_amount;
        this.installment_complete = installment_complete;
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

    public long getCustomer_nid() {
        return customer_nid;
    }

    public void setCustomer_nid(long customer_nid) {
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

    public String getRetail_id() {
        return retail_id;
    }

    public void setRetail_id(String retail_id) {
        this.retail_id = retail_id;
    }

    public String getRetail_name() {
        return retail_name;
    }

    public void setRetail_name(String retail_name) {
        this.retail_name = retail_name;
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

    public int getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(int total_payment) {
        this.total_payment = total_payment;
    }

    public int getTotal_due() {
        return total_due;
    }

    public void setTotal_due(int total_due) {
        this.total_due = total_due;
    }

    public int getEmi_status() {
        return emi_status;
    }

    public void setEmi_status(int emi_status) {
        this.emi_status = emi_status;
    }

    public int getAcknowledgment() {
        return acknowledgment;
    }

    public void setAcknowledgment(int acknowledgment) {
        this.acknowledgment = acknowledgment;
    }

    public String getDevice_status() {
        return device_status;
    }

    public void setDevice_status(String device_status) {
        this.device_status = device_status;
    }

    public int getDevice_lock() {
        return device_lock;
    }

    public void setDevice_lock(int device_lock) {
        this.device_lock = device_lock;
    }

    public String getLock_status() {
        return lock_status;
    }

    public void setLock_status(String lock_status) {
        this.lock_status = lock_status;
    }

    public int getSale_status() {
        return sale_status;
    }

    public void setSale_status(int sale_status) {
        this.sale_status = sale_status;
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

    public String getNext_payment_date() {
        return next_payment_date;
    }

    public void setNext_payment_date(String next_payment_date) {
        this.next_payment_date = next_payment_date;
    }

    public String getNext_payment_amount() {
        return next_payment_amount;
    }

    public void setNext_payment_amount(String next_payment_amount) {
        this.next_payment_amount = next_payment_amount;
    }

    public int getInstallment_complete() {
        return installment_complete;
    }

    public void setInstallment_complete(int installment_complete) {
        this.installment_complete = installment_complete;
    }
}