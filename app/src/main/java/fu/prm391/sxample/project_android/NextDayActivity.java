package fu.prm391.sxample.project_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NextDayActivity extends AppCompatActivity {

    ImageView imgback;
    TextView textViewName;
    ListView listView;
    WeatherAdapter weatherAdapter;
    ArrayList<weather> weathers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_day);
        getDataById();
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        if (city.equals("")) {
            get7DayData("Hanoi");
        } else {
            get7DayData(city);
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailWeather.class);
                weather weather = weathers.get(i);
                intent.putExtra("weathers", weather);
                startActivity(intent);
            }
        });
    }

    private void getDataById() {
        imgback = findViewById(R.id.imageBack);
        textViewName = findViewById(R.id.textViewTenthanhpho);
        listView = findViewById(R.id.listView);
        weathers = new ArrayList<weather>();
        weatherAdapter = new WeatherAdapter(NextDayActivity.this, weathers);
        listView.setAdapter(weatherAdapter);
    }

    public void get7DayData(String data) {
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?city=" + data + "&days=7&key=6cfc86ea9cad40eab7a8ba2ab1c5325a&fbclid=IwAR1UikV5mtjtlUTkeMCEeTnIAgk1gHDgiv64CrJHefxSdEz0zP-yqVJOgGY";
        RequestQueue requestQueue = Volley.newRequestQueue(NextDayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String nameCity = jsonObject.getString("city_name");
                    textViewName.setText(nameCity);
                    JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                    for (int i = 1; i < jsonArrayData.length(); i++) {
                        JSONObject jsonObjectData = jsonArrayData.getJSONObject(i);
                        String ngay = jsonObjectData.getString("datetime");
                        JSONObject jsonObjectWeather = jsonObjectData.getJSONObject("weather");
                        String status = jsonObjectWeather.getString("description");
                        String icon = jsonObjectWeather.getString("icon");
                        String maxTemp = jsonObjectData.getString("max_temp");
                        String minTemp = jsonObjectData.getString("min_temp");

                        String wind = jsonObjectData.getString("wind_spd");
                        Double doubleWind = Double.valueOf(wind);
                        String wind_String = String.valueOf(doubleWind.intValue());

                        String could = jsonObjectData.getString("clouds");
                        String temp = jsonObjectData.getString("temp");
                        Double doubleNhietDo = Double.valueOf(temp);
                        String nhietDo_String = String.valueOf(doubleNhietDo.intValue());
                        String sunRise = jsonObjectData.getString("sunrise_ts");
                        String sunSet = jsonObjectData.getString("sunset_ts");

                        long l1 = Long.valueOf(sunRise);
                        Date date1 = new Date(l1 * 1000L);
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
                        String sun_Rise = simpleDateFormat1.format(date1);


                        long l2 = Long.valueOf(sunSet);
                        Date date2 = new Date(l2 * 1000L);
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
                        String sun_Set = simpleDateFormat2.format(date2);

                        String pressure = jsonObjectData.getString("pres");
                        Double doublePressure = Double.valueOf(pressure);
                        String pressure_String = String.valueOf(doublePressure.intValue());

                        String rh = jsonObjectData.getString("rh");


                        Double a = Double.valueOf(maxTemp);
                        Double b = Double.valueOf(minTemp);
                        String tempMax = String.valueOf(a.intValue());
                        String tempMin = String.valueOf(b.intValue());

                        String day = jsonObjectData.getString("ts");
                        long l3 = Long.valueOf(day);
                        Date date = new Date(l3 * 1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE,yyyy-MM-dd");
                        String dayNow = simpleDateFormat.format(date);

                        String pop = jsonObjectData.getString("pop");
                        Double doublePop = Double.valueOf(pop);
                        String pop_String = String.valueOf(doublePop.intValue());

                        String vis = jsonObjectData.getString("vis");
                        Double doubleVis = Double.valueOf(vis);
                        String vis_String = String.valueOf(doubleVis.intValue());

                        String snow = jsonObjectData.getString("snow");
                        Double doubleSnow = Double.valueOf(snow);
                        String snow_String = String.valueOf(doubleSnow.intValue());


                        weathers.add(new weather(ngay, status, icon, tempMax, tempMin, wind_String, could, nhietDo_String, sun_Rise, sun_Set, pressure_String, rh,nameCity,dayNow,pop_String,vis_String,snow_String));
                    }
                    weatherAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("ketqua", "Json : " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
