package com.example.inweather;

public class CityItemForcecast {


    public String getFore_first_date() {
        return fore_first_date;
    }

    public  void setFore_first_date(String fore_first_date) {
        this.fore_first_date = fore_first_date;
    }

    private  String fore_first_date;

    public String getFore_description() {
        return fore_description;
    }

    public void setFore_description(String fore_description) {
        this.fore_description = fore_description;
    }

    private  String fore_description;

    public String getFore_temp() {
        int curr=  (int)Math.floor (Double.valueOf(fore_temp));
       // return String.valueOf(curr)+"\u00B0";
        return curr+"\u00B0";
    }

    public void setFore_temp(String fore_temp) {
        this.fore_temp = fore_temp;
    }

    private  String fore_temp;

    public String getFore_date() {
        return fore_date;
    }

    public void setFore_date(String fore_date) {
        this.fore_date = fore_date;
    }

    private  String fore_date;

    public String getFore_temp_max() {
        int curr=  (int)Math.floor(Double.valueOf( fore_temp_max));
        return curr+"\u00B0";
    }

    public void setFore_temp_max(String fore_temp_max) {
        this.fore_temp_max = fore_temp_max;
    }

    private String fore_temp_max;








    public   CityItemForcecast(String fore_first_datee,String fore_descriptionn,String tempp,String fore_datee,String fore_temp_max){
        fore_first_date=fore_first_datee;
        this.fore_description=fore_descriptionn;
        this.fore_temp=tempp;
        this.fore_date=fore_datee;
        this.fore_temp_max=fore_temp_max;
    }
}
