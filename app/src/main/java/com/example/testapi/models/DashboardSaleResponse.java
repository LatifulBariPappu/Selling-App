package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class DashboardSaleResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private DashboardData data;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public DashboardData getData() {
        return data;
    }

    public static class DashboardData {
        @SerializedName("total_quantity")
        private int totalQuantity;

        @SerializedName("total_sale")
        private int totalSale;

        @SerializedName("this_month")
        private SalesData thisMonth;

        @SerializedName("this_week")
        private SalesData thisWeek;

        @SerializedName("today")
        private SalesData today;

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public int getTotalSale() {
            return totalSale;
        }

        public SalesData getThisMonth() {
            return thisMonth;
        }

        public SalesData getThisWeek() {
            return thisWeek;
        }

        public SalesData getToday() {
            return today;
        }
    }

    public static class SalesData {
        @SerializedName("total_quantity")
        private int totalQuantity;

        @SerializedName("total_sale")
        private int totalSale;

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public int getTotalSale() {
            return totalSale;
        }
    }
}
