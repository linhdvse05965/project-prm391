package fu.prm391.sxample.project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText editTextSearch;
    TextView textViewCity, textViewCountry, textViewTemp, textViewStatus, textViewHumidity, textViewCloud, textViewWind, textViewDay, textViewSunrise, textViewSunset, textViewApSuat;
    ImageView imgIcon;
    Button btnSearch, btnNextDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getId();
        GetCurrentWeather("Hanoi");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = editTextSearch.getText().toString();
                if (city.equals("")) {
                    GetCurrentWeather("Hanoi");
                } else {
                    GetCurrentWeather(city);
                }

            }
        });
        btnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = editTextSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this, NextDayActivity.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });

    }

    public void GetCurrentWeather(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?city=" + data + "&days=1&key=6cfc86ea9cad40eab7a8ba2ab1c5325a&fbclid=IwAR1UikV5mtjtlUTkeMCEeTnIAgk1gHDgiv64CrJHefxSdEz0zP-yqVJOgGY";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String nameCity = jsonObject.getString("city_name");
                    textViewCity.setText("City name : " + nameCity);
                    String country_code = jsonObject.getString("country_code");
                    textViewCountry.setText("Country code : " + country_code);
                    JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArrayData.length(); i++) {
                        JSONObject jsonObjectData = jsonArrayData.getJSONObject(i);

                        String temp = jsonObjectData.getString("temp");
                        Double doubleNhietDo = Double.valueOf(temp);
                        String nhietDo_String = String.valueOf(doubleNhietDo.intValue());
                        textViewTemp.setText(nhietDo_String + "°C");

                        JSONObject jsonObjectWeather = jsonObjectData.getJSONObject("weather");
                        String icon = jsonObjectWeather.getString("icon");
                        Picasso.with(MainActivity.this).load("https://www.weatherbit.io/static/img/icons/" + icon + ".png").into(imgIcon);

                        String status = jsonObjectWeather.getString("description");
                        textViewStatus.setText(status);

                        String rh = jsonObjectData.getString("rh");
                        textViewHumidity.setText(rh+"%");

                        String could = jsonObjectData.getString("clouds");
                        textViewCloud.setText(could+"%");

                        String wind = jsonObjectData.getString("wind_spd");
                        Double doubleWind = Double.valueOf(wind);
                        String wind_String = String.valueOf(doubleWind.intValue());
                        textViewWind.setText(wind_String+"m/s");

                        String sunRise = jsonObjectData.getString("sunrise_ts");
                        String sunSet = jsonObjectData.getString("sunset_ts");

                        long l1 = Long.valueOf(sunRise);
                        Date date1 = new Date(l1 * 1000L);
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
                        String sun_Rise = simpleDateFormat1.format(date1);
                        textViewSunrise.setText(sun_Rise);


                        long l2 = Long.valueOf(sunSet);
                        Date date2 = new Date(l2 * 1000L);
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
                        String sun_Set = simpleDateFormat2.format(date2);
                        textViewSunset.setText(sun_Set);

                        String pressure = jsonObjectData.getString("pres");
                        Double doublePressure = Double.valueOf(pressure);
                        String pressure_String = String.valueOf(doublePressure.intValue());
                        textViewApSuat.setText(pressure_String+" hPa");

                        SimpleDateFormat sdf = new SimpleDateFormat("EEE,HH:mm aaa", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        textViewDay.setText(currentDateandTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    void getId() {
        editTextSearch = findViewById(R.id.editTextSearch);
        textViewCity = findViewById(R.id.textViewCity);
        textViewCountry = findViewById(R.id.textViewCountry);
        textViewTemp = findViewById(R.id.textViewTemp);
        textViewStatus = findViewById(R.id.textViewStatus);
        textViewHumidity = findViewById(R.id.textViewHumidity);
        textViewCloud = findViewById(R.id.textViewCloud);
        textViewWind = findViewById(R.id.textViewMill);
        textViewSunrise = findViewById(R.id.textViewSunrise);
        textViewSunset = findViewById(R.id.textViewSunset);
        textViewApSuat = findViewById(R.id.textViewApSuat);
        textViewDay = findViewById(R.id.textViewDay);
        imgIcon = findViewById(R.id.imageIcon);
        btnSearch = findViewById(R.id.btnSearch);
        btnNextDay = findViewById(R.id.buttonSeeNextDay);

    }
}
