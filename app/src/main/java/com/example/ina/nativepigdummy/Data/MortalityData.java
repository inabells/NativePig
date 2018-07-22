package com.example.ina.nativepigdummy.Data;

public class MortalityData {
    private String mortality_reg_id;
    private String date_of_death;
    private String cause_of_death;
    private String age;

    public MortalityData(String mortality_reg_id, String date_of_death, String cause_of_death, String age) {
        this.mortality_reg_id = mortality_reg_id;
        this.date_of_death = date_of_death;
        this.cause_of_death = cause_of_death;
        this.age = age;
    }

    public String getMortality_reg_id() {
        return mortality_reg_id;
    }

    public void setMortality_reg_id(String mortality_reg_id) {
        this.mortality_reg_id = mortality_reg_id;
    }

    public String getDate_of_death() {
        return date_of_death;
    }

    public void setDate_of_death(String date_of_death) {
        this.date_of_death = date_of_death;
    }

    public String getCause_of_death() {
        return cause_of_death;
    }

    public void setCause_of_death(String cause_of_death) {
        this.cause_of_death = cause_of_death;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
