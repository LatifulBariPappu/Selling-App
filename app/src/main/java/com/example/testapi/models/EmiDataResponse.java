package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

public class EmiDataResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private EmiData data;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public EmiData getData() {
        return data;
    }

    public static class EmiData {
        @SerializedName("emi_status")
        private EmiStatus emiStatus;

        @SerializedName("collections")
        private Collections collections;

        @SerializedName("locked")
        private Locked locked;

        public EmiStatus getEmiStatus() {
            return emiStatus;
        }

        public Collections getCollections() {
            return collections;
        }

        public Locked getLocked() {
            return locked;
        }
    }

    public static class EmiStatus {
        @SerializedName("emi_running")
        private int emiRunning;

        @SerializedName("emi_completed")
        private int emiCompleted;

        public int getEmiRunning() {
            return emiRunning;
        }

        public int getEmiCompleted() {
            return emiCompleted;
        }
    }

    public static class Collections {
        @SerializedName("total_collection")
        private int totalCollection;

        @SerializedName("total_due")
        private int totalDue;

        public int getTotalCollection() {
            return totalCollection;
        }

        public int getTotalDue() {
            return totalDue;
        }
    }

    public static class Locked {
        @SerializedName("total_unlocked")
        private int totalUnlocked;

        @SerializedName("total_locked")
        private int totalLocked;

        public int getTotalUnlocked() {
            return totalUnlocked;
        }

        public int getTotalLocked() {
            return totalLocked;
        }
    }
}
