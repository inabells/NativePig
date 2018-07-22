package com.example.ina.nativepigdummy.Data;

public class OffspringData {
    private String offspring_id;
    private String sex;
    private String birthweight;
    private String weaningweight;

    public OffspringData(String offspring_id, String sex, String birthweight, String weaningweight) {
        this.offspring_id = offspring_id;
        this.sex = sex;
        this.birthweight = birthweight;
        this.weaningweight = weaningweight;
    }

    public String getOffspring_id() {
        return offspring_id;
    }

    public void setOffspring_id(String offspring_id) {
        this.offspring_id = offspring_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthweight() {
        return birthweight;
    }

    public void setBirthweight(String birthweight) {
        this.birthweight = birthweight;
    }

    public String getWeaningweight() {
        return weaningweight;
    }

    public void setWeaningweight(String weaningweight) {
        this.weaningweight = weaningweight;
    }
}
