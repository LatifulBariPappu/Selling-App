package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmiScheduleModel {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("emi_schedule")
    List<EmiSchedule> emiScheduleList;

    public EmiScheduleModel(String message, int status, List<EmiSchedule> emiScheduleList) {
        this.message = message;
        this.status = status;
        this.emiScheduleList = emiScheduleList;
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

    public List<EmiSchedule> getEmiScheduleList() {
        return emiScheduleList;
    }

    public void setEmiScheduleList(List<EmiSchedule> emiScheduleList) {
        this.emiScheduleList = emiScheduleList;
    }

    public static class EmiSchedule{
        @SerializedName("installment_number")
        private int installment_number;
        @SerializedName("imei_1")
        private String imei_1;
        @SerializedName("installment_amount")
        private int installment_amount;
        @SerializedName("installment_date")
        private String installment_date;

        public EmiSchedule(int installment_number, String imei_1, int installment_amount, String installment_date) {
            this.installment_number = installment_number;
            this.imei_1 = imei_1;
            this.installment_amount = installment_amount;
            this.installment_date = installment_date;
        }

        public int getInstallment_number() {
            return installment_number;
        }

        public void setInstallment_number(int installment_number) {
            this.installment_number = installment_number;
        }

        public String getImei_1() {
            return imei_1;
        }

        public void setImei_1(String imei_1) {
            this.imei_1 = imei_1;
        }

        public int getInstallment_amount() {
            return installment_amount;
        }

        public void setInstallment_amount(int installment_amount) {
            this.installment_amount = installment_amount;
        }

        public String getInstallment_date() {
            return installment_date;
        }

        public void setInstallment_date(String installment_date) {
            this.installment_date = installment_date;
        }
    }

}
