package com.example.testapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestrictedPolicyRequestModel {
    @SerializedName("policy")
    private List<Policy> policy;
    @SerializedName("apppolicy")
    private List<AppPolicy> apppolicy;
    @SerializedName("networkpolicy")
    private List<NetworkPolicy> networkpolicy;

    public List<Policy> getPolicy() {
        return policy;
    }

    public void setPolicy(List<Policy> policy) {
        this.policy = policy;
    }

    public List<AppPolicy> getApppolicy() {
        return apppolicy;
    }

    public void setApppolicy(List<AppPolicy> apppolicy) {
        this.apppolicy = apppolicy;
    }

    public List<NetworkPolicy> getNetworkpolicy() {
        return networkpolicy;
    }

    public void setNetworkpolicy(List<NetworkPolicy> networkpolicy) {
        this.networkpolicy = networkpolicy;
    }

    public static class Policy{
        @SerializedName("policyName")
        private String policyName;
        @SerializedName("imei")
        private String imei;

        public String getPolicyName() {
            return policyName;
        }

        public void setPolicyName(String policyName) {
            this.policyName = policyName;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }
    }
    public static class AppPolicy{
        @SerializedName("appName")
        private String appName;
        @SerializedName("imei")
        private String imei;
        @SerializedName("packageName")
        private String packageName;
        @SerializedName("appStatus")
        private String appStatus;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getAppStatus() {
            return appStatus;
        }

        public void setAppStatus(String appStatus) {
            this.appStatus = appStatus;
        }
    }
    public static class NetworkPolicy{
        @SerializedName("policyName")
        private String policyName;
        @SerializedName("imei")
        private String imei;

        public String getPolicyName() {
            return policyName;
        }

        public void setPolicyName(String policyName) {
            this.policyName = policyName;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }
    }
}
