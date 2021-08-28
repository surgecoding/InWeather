package com.example.inweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Debug;
import android.os.Parcelable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver br ;
    TimeProcess tp = new TimeProcess();
    private  String apikey =BuildConfig.API_KEY;

   static String search_city_title="gweru";
  // String search_current_weather = "http://api.openweathermap.org/data/2.5/weather?q="+search_city_title+"&appid=""&units=metric";

  // String search_forcast_weather = "http://api.openweathermap.org/data/2.5/forecast?q="+search_city_title+"&appid=b0024c474d4f6c0b8ec6e9e1415eedc6&units=metric";

    Map<String,String> filtered=new HashMap<>();
    LinearLayoutManager layout_m;

    TextView current_main;
    TextView city_name;
    TextView sv_text;
    TextView wind_speed_screen;
    TextView wind_direction_screen;



    ImageView main_pic;

    TextView current_temp;

    TextView fore_first_screen;

    TextView country_screen;

    Bundle recycler_bundle;

    TextView main_fore_label;
    Toolbar tool_bar;


    LinearLayout lin;

 public static   RecyclerView recyclerView;

public static ForeCastAdapter foreCastAdapter;
    String degree_symbol="\u00B0";


    HostnameVerifier hostv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!Connected(MainActivity.this))
        {Alertconnection(MainActivity.this).show();
        }
        else {
            //  Toast.makeText(MainActivity.this,"Welcome", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);

        }



        setContentView(R.layout.activity_main);
        current_main = findViewById(R.id.current_main);
        city_name = findViewById(R.id.city_name);
        current_temp = findViewById(R.id.current_temp);

        wind_speed_screen=findViewById(R.id.wind_speed_screen);
        wind_direction_screen=findViewById(R.id.wind_direction_screen);
        country_screen=findViewById(R.id.current_country);
        layout_m = new LinearLayoutManager(this);

        tool_bar=findViewById(R.id.tool_bar);

        setSupportActionBar(tool_bar);

        foreCastAdapter =new ForeCastAdapter();

       // recyclerView = findViewById(R.id.recyclerView);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(foreCastAdapter);


       recyclerView.setLayoutManager(layout_m);


        main_pic=findViewById(R.id.main_pic);

      //  lin=findViewById(R.id.lin);


     //   lin.setVisibility(View.GONE);

        if(savedInstanceState==null) {
            loadCurrentWeather(search_city_title);

        }else{
            Parcelable recycler_state = savedInstanceState.getParcelable("recycler_state");
            recyclerView.getLayoutManager().onRestoreInstanceState(recycler_state);
        }

        verify();
    }


    void loadCurrentWeather(String city){

        String search_current_weather = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey+"&units=metric";

        String search_forcast_weather = "http://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+apikey+"&units=metric";
      //  http://api.openweathermap.org/data/2.5/forecast?q= bulawayo & appid=b0024c474d4f6c0b8ec6e9e1415eedc6 & units=metric




        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Weather...");
        pd.show();
        RequestQueue requestQue = Volley.newRequestQueue(this);



        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                search_current_weather, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonFromString = new JSONObject(response);
                    String code = jsonFromString.getString("cod");

                    if(code=="404"){
                        Toast.makeText(getApplicationContext(),"Please enter a valid city", Toast.LENGTH_LONG).show();

                    }
                    else if(code!="404") {

                        JSONArray Jarrayfromobj = jsonFromString.getJSONArray("weather");

                        JSONObject weather_array_object = Jarrayfromobj.getJSONObject(0);

                        JSONObject json_wind_speed = jsonFromString.getJSONObject("wind");

                        JSONObject jcountry = jsonFromString.getJSONObject("sys");
                        String country = jcountry.getString("country");

                        String description = weather_array_object.getString("description");

                        String wind_speed = json_wind_speed.getString("speed");
                        String wind_direction = json_wind_speed.getString("deg");


                        String name = jsonFromString.getString("name");
                        JSONObject job_main_temp = jsonFromString.getJSONObject("main");
                        String current_temperature = job_main_temp.getString("temp");
                        String icon_type = weather_array_object.getString("icon");
                        loadMainPic(description, icon_type);

                        new CityItem(description, name, current_temperature, icon_type, wind_speed, wind_direction, country);
                        current_main.setText(CityItem.getMain_weather());
                        city_name.setText(CityItem.getName());
                        current_temp.setText(CityItem.getCurrent_temperature());
                        wind_speed_screen.setText(CityItem.getWind_speed());
                        wind_direction_screen.setText(CityItem.getWind_direction());
                        country_screen.setText(CityItem.getCurrent_country());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                lin.setVisibility(View.VISIBLE);

                pd.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"An error occured,check if you are coontected" +
                        "to the internet or your city name is valid",Toast.LENGTH_LONG).show();
//        lin.setVisibility(View.INVISIBLE);
            }
        }

        );


        foreCastAdapter.notifyDataSetChanged();


        StringRequest stringForecastRe=new StringRequest(Request.Method.GET,
                search_forcast_weather, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonFromString = new JSONObject(response);
                    String code = jsonFromString.getString("cod");
                    if(code=="404"){
                        Toast.makeText(getApplicationContext(),"Please enter a valid city", Toast.LENGTH_LONG).show();

                    }


                    else if(code!="404") {
                        JSONArray jsonArray = jsonFromString.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobj_first = jsonArray.getJSONObject(i);
                            String first_date = jobj_first.getString("dt_txt");

                            //*****************to get temp below////***
                            JSONArray array_weather = jobj_first.getJSONArray("weather");
                            JSONObject obj_main = jobj_first.getJSONObject("main");

                            JSONObject tempOBJ = array_weather.getJSONObject(0);
                            String temp = obj_main.getString("temp_min");
                            String temp_max = obj_main.getString("temp_max");

                            String description = tempOBJ.getString("description");
//*************************************temp above///************************

                            String processed_date = TimeProcess.setToprocess(first_date);

                            Log.i("DAYDREAMED", first_date);
                            Log.i("-----------", processed_date);

                            String hour_of_day = TimeProcess.setTimeOfDay(first_date);

                            ForeCastTemp.getForecastlist();

                            if (filtered.containsKey(processed_date)) {

                            } else if (!(filtered.containsKey(processed_date)) && TimeProcess.compare() == false) {
                                filtered.put(processed_date, processed_date);
                                ForeCastTemp.getForecastlist().add(new CityItemForcecast(filtered.get(processed_date), description, temp, hour_of_day, temp_max));



                            }
//ForeCastTemp.setForecastlist_final();
                           foreCastAdapter.notifyDataSetChanged();


                        }



                    //     recyclerView = findViewById(R.id.recyclerView);
                    //    recyclerView.setAdapter(foreCastAdapter);

                       recyclerView.setLayoutManager(layout_m);

                        //  recyclerView.setAdapter(foreCastAdapter);

                       foreCastAdapter.notifyDataSetChanged();


                    }

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),"An error occured,check if you are coontected" +
                        "to the internet or your city name is valid",Toast.LENGTH_LONG).show();
               // lin.setVisibility(View.INVISIBLE);

            }
        }

        );

        requestQue.add(stringRequest);
        requestQue.add(stringForecastRe);


    }

    void verify(){
        hostv=new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return false;
            }
        };
    }



    void loadMainPic(String main,String icon_type){
        if(main.equalsIgnoreCase("clear sky")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.day_clear);
        }
      else  if(main.equalsIgnoreCase("clear sky")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.night_clear);
        }

        else  if(main.equalsIgnoreCase("overcast clouds")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.overcast);
        }

        else  if(main.equalsIgnoreCase("overcast clouds")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.overcast);
        }


        else  if(main.equalsIgnoreCase("scattered clouds")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.cloudy);
        }

        else  if(main.equalsIgnoreCase("scattered clouds")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.cloudy);
        }


        else  if(main.equalsIgnoreCase("broken clouds")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.day_partial_cloud);
        }

        else  if(main.equalsIgnoreCase("broken clouds")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.night_partial_cloud);
        }


        else  if(main.equalsIgnoreCase("shower rain")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.day_sleet);
        }

        else  if(main.equalsIgnoreCase("shower rain")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.night_sleet);
        }


        else  if(main.equalsIgnoreCase("rain")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.day_rain);
        }

        else  if(main.equalsIgnoreCase("rain")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.night_rain);
        }

        else  if(main.equalsIgnoreCase("thunderstorm")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.day_rain_thunder);
        }

        else  if(main.equalsIgnoreCase("thunderstorm")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.night_rain_thunder);
        }



        else  if(main.equalsIgnoreCase("snow")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.day_snow);
        }

        else  if(main.equalsIgnoreCase("snow")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.night_snow);
        }



        else  if(main.equalsIgnoreCase("light rain")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.day_rain);
        }

        else  if(main.equalsIgnoreCase("light rain")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.night_rain);
        }


        else  if(main.equalsIgnoreCase("heavy rain")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.day_rain_thunder);
        }

        else  if(main.equalsIgnoreCase("heavy rain")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.night_rain_thunder);
        }

        else  if(main.equalsIgnoreCase("few clouds")&&icon_type.contains("d")){
            main_pic.setImageResource(R.drawable.day_partial_cloud);
        }

        else  if(main.equalsIgnoreCase("few clouds")&&icon_type.contains("n")){
            main_pic.setImageResource(R.drawable.night_partial_cloud);
        }








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inf = new MenuInflater(this);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem si = menu.findItem(R.id.option_search_item);
                if(si!=null) {
           final androidx.appcompat.widget.SearchView sv= (androidx.appcompat.widget.SearchView)MenuItemCompat.getActionView(si);


           sv.setQueryHint("Type City name");
          sv.setMaxWidth(Integer.MAX_VALUE);
           sv_text=(TextView) sv.findViewById(androidx.appcompat.R.id.search_src_text);

           sv_text.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
search_city_title=query.toLowerCase();
setCity(search_city_title);
if(foreCastAdapter!=null&&ForeCastTemp.getForecastlist()!=null) {
    ForeCastTemp.getForecastlist().clear();
    filtered.clear();
foreCastAdapter.notifyDataSetChanged();

}
try {
    loadCurrentWeather(query.toLowerCase());
}catch(Exception r){
  /*  Toast.makeText(getApplicationContext(),"An error occured,check if you are coontected" +
            "to the internet or your city name is valid",Toast.LENGTH_LONG).show();
*/

}


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
});

                }
        return true;
    }

void setCity(String city){
        this.search_city_title=city;
}


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        recycler_bundle=new Bundle();
        Parcelable liststate = recyclerView.getLayoutManager().onSaveInstanceState();
        // layout_m=GridLayoutManager.
        recycler_bundle.putParcelable("recycler_state",liststate);
        outState.putParcelable("recycle_state",liststate);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState!=null){
            Parcelable recycler_state = savedInstanceState.getParcelable("recycler_state");
            recyclerView.getLayoutManager().onRestoreInstanceState(recycler_state);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();




    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public boolean Connected(Context context){
        ConnectivityManager conm= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conm.getActiveNetworkInfo();
        if(net!=null&&net.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi = conm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = conm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        }else
                return false;
    }


    public AlertDialog.Builder Alertconnection(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet");

        builder.setMessage("Make sure you have Mobile Data or wifi connection.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;

    }






    }