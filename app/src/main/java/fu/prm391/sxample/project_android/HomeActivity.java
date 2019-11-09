package fu.prm391.sxample.project_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    LinearLayout linearLayoutWeather;
    TextView textViewLocationHome, textViewMaxTempHome, textViewMinTempHome, textViewTempHome;
    ImageView imageIconHome;
    CalendarView calendarView;
    FirebaseFirestore db;
    DialogInterface.OnClickListener diglogClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getDataById();
        db = FirebaseFirestore.getInstance();
        linearLayoutWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        getWeather("Ha Noi");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                final String date = i2 + "/" + (i1 + 1) + "/" + i;
                db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            for (QueryDocumentSnapshot doc : querySnapshot) {
                                if (date.toString().equals(doc.getString("date"))) {
                                    Intent intent = new Intent(HomeActivity.this, CalendarEventActivity.class);
                                    intent.putExtra("date", date);
                                    intent.putExtra("event",doc.getString("event"));
                                    startActivity(intent);

                                } else {
                                     diglogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            switch (i) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    Intent intent2 = new Intent(HomeActivity.this,AddEventCalendarActivity.class);
                                                    intent2.putExtra("date", date);
                                                    startActivity(intent2);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    break;
                                            }

                                        }
                                    };
                                }
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                            builder.setMessage("There were no events that day. Do you want add new events for this day?").setPositiveButton("Yes", diglogClickListener)
                                    .setNegativeButton("No", diglogClickListener).show();
                        }
                    }
                });


            }
        });

    }

    private void getDataById() {
        textViewLocationHome = findViewById(R.id.textViewLocationHome);
        textViewMaxTempHome = findViewById(R.id.textViewMaxTempHome);
        textViewMinTempHome = findViewById(R.id.textViewMinTempHome);
        textViewTempHome = findViewById(R.id.textViewTempHome);
        imageIconHome = findViewById(R.id.imageIconHome);
        linearLayoutWeather = findViewById(R.id.weatherlayout);
        calendarView = findViewById(R.id.Calender);
    }

    public void getWeather(String data) {
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
                        textViewMaxTempHome.setText(tempMax + "°C");
                        textViewMinTempHome.setText(tempMin + "°C");
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
