package fu.prm391.sxample.project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        // String url = "http://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=4452c0a5d0c20a985f25c359aa7fb58c";
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?city=" + data + "&days=7&key=6cfc86ea9cad40eab7a8ba2ab1c5325a&fbclid=IwAR1UikV5mtjtlUTkeMCEeTnIAgk1gHDgiv64CrJHefxSdEz0zP-yqVJOgGY";
        RequestQueue requestQueue = Volley.newRequestQueue(NextDayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
//                    String name = jsonObjectCity.getString("name");
//                    textViewName.setText(name);
//                    JSONArray jsonArrayList = jsonObject.getJSONArray("list");
//                    for (int i = 0; i < jsonArrayList.length(); i++) {
//                        JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
//                        String ngay = jsonObjectList.getString("dt");
//                        long l = Long.valueOf(ngay);
//                        Date date = new Date(l * 1000L);
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
//                        String dayNow = simpleDateFormat.format(date);
//
//                        JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
//                        String maxTemp = jsonObjectTemp.getString("temp_max");
//                        String minTemp = jsonObjectTemp.getString("temp_min");
//                        Double a = Double.valueOf(maxTemp);
//                        Double b = Double.valueOf(minTemp);
//                        String tempMax = String.valueOf(a.intValue());
//                        String tempMin = String.valueOf(b.intValue());
//
//                        JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
//                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
//                        String status = jsonObjectWeather.getString("description");
//                        String icon = jsonObjectWeather.getString("icon");
//                        weathers.add(new weather(dayNow, status, icon, tempMax, tempMin));
//
//                    }
//
//                    weatherAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // JSONObject jsonObjectCity = jsonObject.getJSONObject("city_name");
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
                        Double a = Double.valueOf(maxTemp);
                        Double b = Double.valueOf(minTemp);
                        String tempMax = String.valueOf(a.intValue());
                        String tempMin = String.valueOf(b.intValue());
                        weathers.add(new weather(ngay, status, icon, tempMax, tempMin));
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
