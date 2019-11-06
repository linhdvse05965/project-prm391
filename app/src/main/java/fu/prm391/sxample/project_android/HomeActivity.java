package fu.prm391.sxample.project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class HomeActivity extends AppCompatActivity {
LinearLayout linearLayoutWeather;
TextView textViewLocationHome,textViewMaxTempHome,textViewMinTempHome,textViewTempHome;
ImageView imageIconHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getDataById();
        linearLayoutWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        getWeather("Ha Noi");
    }
    private void getDataById(){
        textViewLocationHome = findViewById(R.id.textViewLocationHome);
        textViewMaxTempHome = findViewById(R.id.textViewMaxTempHome);
        textViewMinTempHome = findViewById(R.id.textViewMinTempHome);
        textViewTempHome = findViewById(R.id.textViewTempHome);
        imageIconHome = findViewById(R.id.imageIconHome);
        linearLayoutWeather = findViewById(R.id.weatherlayout);
    }
    public void getWeather(String data){
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?city=" + data + "&days=1&key=6cfc86ea9cad40eab7a8ba2ab1c5325a&fbclid=IwAR1UikV5mtjtlUTkeMCEeTnIAgk1gHDgiv64CrJHefxSdEz0zP-yqVJOgGY";
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String nameCity = jsonObject.getString("city_name");
                    textViewLocationHome.setText(nameCity);
                    JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArrayData.length(); i++) {
                        JSONObject jsonObjectData = jsonArrayData.getJSONObject(i);

                        String temp = jsonObjectData.getString("temp");
                        Double doubleNhietDo = Double.valueOf(temp);
                        String nhietDo_String = String.valueOf(doubleNhietDo.intValue());
                        textViewTempHome.setText(nhietDo_String + "°C");

                        JSONObject jsonObjectWeather = jsonObjectData.getJSONObject("weather");
                        String icon = jsonObjectWeather.getString("icon");
                        Picasso.with(HomeActivity.this).load("https://www.weatherbit.io/static/img/icons/" + icon + ".png").into(imageIconHome);

                        String maxTemp = jsonObjectData.getString("max_temp");
                        String minTemp = jsonObjectData.getString("min_temp");
                        Double a = Double.valueOf(maxTemp);
                        Double b = Double.valueOf(minTemp);
                        String tempMax = String.valueOf(a.intValue());
                        String tempMin = String.valueOf(b.intValue());
                        textViewMaxTempHome.setText(tempMax+"°C");
                        textViewMinTempHome.setText(tempMin+"°C");
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
}
