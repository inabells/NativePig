package com.example.ina.nativepigdummy.Data;

public class SalesData {
    private String sales_reg_id;
    private String date_sold;
    private String weight;
    private String price;
    private String age;

    public SalesData(){

    }

    public SalesData(String sales_reg_id, String date_sold, String weight, String price, String age) {
        this.sales_reg_id = sales_reg_id;
        this.date_sold = date_sold;
        this.weight = weight;
        this.price = price;
        this.age = age;
    }

    public String getSales_reg_id() {
        return sales_reg_id;
    }

    public void setSales_reg_id(String sales_reg_id) {
        this.sales_reg_id = sales_reg_id;
    }

    public String getDate_sold() {
        return date_sold;
    }

    public void setDate_sold(String date_sold) {
        this.date_sold = date_sold;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
