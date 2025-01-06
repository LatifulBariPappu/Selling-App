package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class DashboardSaleResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private Data data;

    public DashboardSaleResponse(String message, int status, Data data) {
        this.message = message;
        this.status = status;
        this.data = data;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("total")
        private Total total;

        @SerializedName("this_month")
        private ThisMonth this_month;
        @SerializedName("this_week")
        private ThisWeek this_week;
        @SerializedName("today")
        private Today today;

        public Data(Total total, ThisMonth this_month, ThisWeek this_week, Today today) {
            this.total = total;
            this.this_month = this_month;
            this.this_week = this_week;
            this.today = today;
        }

        public Total getTotal() {
            return total;
        }

        public void setTotal(Total total) {
            this.total = total;
        }

        public ThisMonth getThis_month() {
            return this_month;
        }

        public void setThis_month(ThisMonth this_month) {
            this.this_month = this_month;
        }

        public ThisWeek getThis_week() {
            return this_week;
        }

        public void setThis_week(ThisWeek this_week) {
            this.this_week = this_week;
        }

        public Today getToday() {
            return today;
        }

        public void setToday(Today today) {
            this.today = today;
        }

        public static class Total {
            @SerializedName("total_quantity")
            private int total_quantity;
            @SerializedName("total_sale")
            private int total_sale;

            public Total(int total_quantity, int total_sale) {
                this.total_quantity = total_quantity;
                this.total_sale = total_sale;
            }

            public int getTotal_quantity() {
                return total_quantity;
            }

            public void setTotal_quantity(int total_quantity) {
                this.total_quantity = total_quantity;
            }

            public int getTotal_sale() {
                return total_sale;
            }

            public void setTotal_sale(int total_sale) {
                this.total_sale = total_sale;
            }
        }

        public static class ThisMonth {

            @SerializedName("total_quantity")
            private int total_quantity;
            @SerializedName("total_sale")
            private int total_sale;

            public ThisMonth(int total_quantity, int total_sale) {
                this.total_quantity = total_quantity;
                this.total_sale = total_sale;
            }

            public int getTotal_quantity() {
                return total_quantity;
            }

            public void setTotal_quantity(int total_quantity) {
                this.total_quantity = total_quantity;
            }

            public int getTotal_sale() {
                return total_sale;
            }

            public void setTotal_sale(int total_sale) {
                this.total_sale = total_sale;
            }
        }

        public static class ThisWeek {
            @SerializedName("total_quantity")
            private int total_quantity;
            @SerializedName("total_sale")
            private int total_sale;

            public ThisWeek(int total_quantity, int total_sale) {
                this.total_quantity = total_quantity;
                this.total_sale = total_sale;
            }

            public int getTotal_quantity() {
                return total_quantity;
            }

            public void setTotal_quantity(int total_quantity) {
                this.total_quantity = total_quantity;
            }

            public int getTotal_sale() {
                return total_sale;
            }

            public void setTotal_sale(int total_sale) {
                this.total_sale = total_sale;
            }
        }

        public static class Today {

            @SerializedName("total_quantity")
            private int total_quantity;
            @SerializedName("total_sale")
            private int total_sale;

            public Today(int total_quantity, int total_sale) {
                this.total_quantity = total_quantity;
                this.total_sale = total_sale;
            }

            public int getTotal_quantity() {
                return total_quantity;
            }

            public void setTotal_quantity(int total_quantity) {
                this.total_quantity = total_quantity;
            }

            public int getTotal_sale() {
                return total_sale;
            }

            public void setTotal_sale(int total_sale) {
                this.total_sale = total_sale;
            }
        }
    }
}
