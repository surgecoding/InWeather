package com.example.inweather;

import java.util.ArrayList;

public class ForeCastTemp {


    public static ArrayList<CityItemForcecast> getForecastlist() {


        return forecastlist;
    }

    public static void setForecastlist(ArrayList<CityItemForcecast> forecastlist) {
        ForeCastTemp.forecastlist = forecastlist;
    }

    private static ArrayList<CityItemForcecast>forecastlist=new ArrayList<CityItemForcecast>();

    public static ArrayList<CityItemForcecast> getForecastlist_final() {


        return forecastlist_final;
    }

    public static void setForecastlist_final() {
int i=0;
            while(forecastlist.size()>2) {

                if (forecastlist.get(Math.abs(i)).getFore_first_date().equalsIgnoreCase(forecastlist.get(Math.abs(i-1)).getFore_first_date())) {
                    forecastlist.remove(i);
                    forecastlist_final.add(forecastlist.get(i));
                }  else if(!(forecastlist.get(i).getFore_first_date().equalsIgnoreCase(forecastlist.get(i + 1).getFore_first_date()))) {


                }
                i++;
            }


    }

    public static ArrayList<CityItemForcecast>forecastlist_final=new ArrayList<CityItemForcecast>();



}
