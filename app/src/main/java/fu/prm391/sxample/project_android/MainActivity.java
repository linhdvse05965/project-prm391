package fu.prm391.sxample.project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText editTextSearch;
    TextView textViewCity, textViewCountry, textViewTemp, textViewStatus, textViewHumidity, textViewCloud, textViewWind, textViewDay;
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
                if(city.equals("")){
                    GetCurrentWeather("Hanoi");
                }else{
                    GetCurrentWeather(city);
                }

            }
        });
        btnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = editTextSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this , NextDayActivity.class);
                intent.putExtra("name",city);
                startActivity(intent);
            }
        });
    }

    public void GetCurrentWeather(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=4452c0a5d0c20a985f25c359aa7fb58c";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String day = jsonObject.getString("dt");
                    String cityName = jsonObject.getString("name");
                    textViewCity.setText("City name : " + cityName);

                    long l = Long.valueOf(day);
                    Date date = new Date(l*1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE yyyy-MM-dd HH:mm:ss");
                    String dayNow = simpleDateFormat.format(date);
                    textViewDay.setText(dayNow);
                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String status = jsonObjectWeather.getString("description");
                    String icon = jsonObjectWeather.getString("icon");
                    Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imgIcon);
                    textViewStatus.setText(status);
                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    String nhietDo = jsonObjectMain.getString("temp");
                    String doAm = jsonObjectMain.getString("humidity");
                    Double doubleNhietDo = Double.valueOf(nhietDo);
                    String nhietDo_String = String.valueOf(doubleNhietDo.intValue());
                    textViewTemp.setText(nhietDo_String+"°C");
                    textViewHumidity.setText(doAm+"%");

                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String gio = jsonObjectWind.getString("speed");
                    textViewWind.setText(gio+"m/s");

                    JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                    String may = jsonObjectCloud.getString("all");
                    textViewCloud.setText(may+"%");
                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                    String country = jsonObjectSys.getString("country");
                    textViewCountry.setText("Country name : "+country);
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
        textViewDay = findViewById(R.id.textViewDay);
        imgIcon = findViewById(R.id.imageIcon);
        btnSearch = findViewById(R.id.btnSearch);
        btnNextDay = findViewById(R.id.buttonSeeNextDay);
    }
}
