package com.example.inweather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeProcess {
    //This class handles conversion of times etc
  static  String final_day;


    static Date date_out;

 static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

 private   static Calendar today = Calendar.getInstance();
    private static int day_of_week;

 public  static  String setTimeOfDay(String date_Input) throws ParseException {
     String final_hour="no";
  Date   hr_out = format.parse(date_Input);

  Calendar cal = Calendar.getInstance();



     cal.setTime(hr_out);



 int hr= hr_out.getHours();
 int min = hr_out.getMinutes();

 day_of_week =hr_out.getDay();

 String hour;
 String minute;

 if(hr<10&&min<10){
final_hour=String.valueOf(hr)+":"+"00"+String.valueOf(min);
 }
 if(min<10&&min>=10){
final_hour= "00"+String.valueOf(hr)+":"+String.valueOf(min);
 }
 else if(hr>=10&&min>=10){
     final_hour=String.valueOf(hr)+":"+String.valueOf(min);
 }
 else{
     final_hour=String.valueOf(hr)+":"+String.valueOf(min);
 }


  return final_hour;

 }
    public static String setToprocess(String date_Input) throws ParseException {


        date_out = format.parse(date_Input);
int date = date_out.getDay();
        String day=(String) android.text.format.DateFormat.format("dd",date) ;
//System.out.print(day);

switch(date){
    case 0:final_day= "Sun";
          break;
    case(1):final_day="Mon";break;
    case(2):final_day= "Tue";break;
    case(3):final_day= "Wed";break;
    case(4):final_day= "Thur";break;
    case(5):final_day= "Fri";break;
    case(6):final_day= "Sat";break;

    default:final_day="unknown";
}
return final_day;
    }

    public   String getProcessed()
    {
        return final_day;
    }


    public static boolean compare(){
  int today_raw=  today.getTime().getDay();
 // System.out.print("today raw --"+today_raw);
     if(!(day_of_week==today_raw)){
         return false;
        }
     else
         return true;
    }
}
