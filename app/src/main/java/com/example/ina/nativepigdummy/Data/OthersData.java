package com.example.ina.nativepigdummy.Data;

public class OthersData {
    private String others_reg_id;
    private String date_removed;
    private String reason;
    private String age;

    public OthersData(String others_reg_id, String date_removed, String reason, String age) {
        this.others_reg_id = others_reg_id;
        this.date_removed = date_removed;
        this.reason = reason;
        this.age = age;
    }

    public String getOthers_reg_id() {
        return others_reg_id;
    }

    public void setOthers_reg_id(String others_reg_id) {
        this.others_reg_id = others_reg_id;
    }

    public String getDate_removed() {
        return date_removed;
    }

    public void setDate_removed(String date_removed) {
        this.date_removed = date_removed;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

