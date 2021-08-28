package com.example.inweather;

import android.os.Debug;
import android.util.Log;

public class CityItem {

    public static String getMain_weather() {
        return main_weather;
    }

    public static void setMain_weather(String mainweather) {
         main_weather = mainweather;
    }

    private static String main_weather;
    private static String descrption;
    private static String country;


    public static String getWind_direction() {
        String out="";
        double deg = Double.parseDouble(wind_direction);
        Log.i("WIND DIR", wind_direction);


        if(deg>0&&deg<=11.25){
            return"N";
        }

      if(deg>11.25&&deg<=33.75){
            return"NNE";
        }


       else  if(deg>33.75&&deg<=56.25){
           return"NE";
       }


       else  if(deg>56.25&&deg<=78.75){
           return"ENE";
       }

       else  if(deg>78.25&&deg<=101.25){
           return"E";
       }

       else  if(deg>101.25&&deg<=123.75){
           return "ENE";
       }
       else  if(deg>123.75&& deg <=146.25){
           return "SE";
       }
       else  if(deg>146.25&&deg<=168.75){
           return "SSE";
       }

       else  if(deg>165.75&&deg<=191.25){
           return "S";
       }


       else  if(deg>191.25&&deg<=213.75){
           return "SSW";
       }


       else  if(deg>213.75&&deg<=236.25){

           return "SW";
       }

       else  if(deg>236.25&&deg<=258.75){
           return "WSW";
       }

       else  if(deg>258.75&&deg<=281.25){
           return "W";
       }


       else  if(deg>281.25&&deg<=303.75){
           return "WNW";
       }

       else  if(deg>303.75&&deg<=326.25){
           return "NW";
       }

       else  if(deg>326.25&&deg<=348.75){
          return "NNW";

       }


      else if(deg>348.75&&deg<360){

          return"N";
      }

        return  "";
    }

    public static void setWind_direction(String wind_direction) {
        CityItem.wind_direction = wind_direction;
    }

    private static String wind_direction;



    public static String getIcon_type() {
        return icon_type;
    }

    public static void setIcon_type(String icon_type) {
        CityItem.icon_type = icon_type;
    }

    private static String icon_type;



    public static String getCurrent_temperature() {
     int curr=  (int)Math.ceil (Double.valueOf( current_temperature));
        return String.valueOf(curr)+"\u00B0";
    }

    public static void setCurrent_temperature(String current_temperature) {
        CityItem.current_temperature = current_temperature;
    }

    private static String current_temperature;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        CityItem.name = name;
    }

    private static String name;
    private static String humidity;

    private  static String wind_speed;

    public static String getCurrent_country() {
        return current_country;
    }

    public static void setCurrent_country(String current_country) {
        CityItem.current_country = current_country;
    }

    private static String current_country;

    public static String getWind_speed() {
          double outspeed = Double.valueOf(wind_speed);
          wind_speed = String.valueOf(Math.round(outspeed*10.0)/10.0);
        return wind_speed;
    }

    public static void setWind_speed(String wind_speed) {
        CityItem.wind_speed = wind_speed;
    }

    public  CityItem(String mainweather, String city_name, String currenttemp, String icon_typee,String wiind_speede,String wind_directionn,String current_countryy){
        name=city_name;
        main_weather=mainweather;
        current_temperature=currenttemp;
        icon_type=icon_typee;

        wind_speed=wiind_speede;
        wind_direction=wind_directionn;
        current_country =current_countryy;

    }

}
