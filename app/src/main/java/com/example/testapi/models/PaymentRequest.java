package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class PaymentRequest {
    @SerializedName("imei_1")
    private String imei_1;
    @SerializedName("payment_amount")
    private int payment_amount;
    @SerializedName("payment_date")
    private String payment_date;

    public PaymentRequest(String imei_1, int payment_amount, String payment_date) {
        this.imei_1 = imei_1;
        this.payment_amount = payment_amount;
        this.payment_date = payment_date;
    }

    public String getImei_1() {
        return imei_1;
    }

    public void setImei_1(String imei_1) {
        this.imei_1 = imei_1;
    }

    public int getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(int payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }
}
