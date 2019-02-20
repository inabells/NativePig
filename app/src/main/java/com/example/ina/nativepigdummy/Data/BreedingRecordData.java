package com.example.ina.nativepigdummy.Data;

public class BreedingRecordData {
    private String sow_id;
    private String boar_id;
    private String date_bred;

    public BreedingRecordData(String sow_id, String boar_id, String date_bred) {
        this.sow_id = sow_id;
        this.boar_id = boar_id;
        this.date_bred = date_bred;
    }

    public BreedingRecordData(){

    }

    public String getSow_id() {
        return sow_id;
    }

    public void setSow_id(String sow_id) {
        this.sow_id = sow_id;
    }

    public String getBoar_id() {
        return boar_id;
    }

    public void setBoar_id(String boar_id) {
        this.boar_id = boar_id;
    }

    public String getDate_bred() {
        return date_bred;
    }

    public void setDate_bred(String date_bred) {
        this.date_bred = date_bred;
    }
}
