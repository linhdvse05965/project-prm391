package fu.prm391.sxample.project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailWeather extends AppCompatActivity {
    private TextView textViewDateD, textViewStatusD, textViewWindD, textViewCloudD, textViewTempD, textViewSunRiseD, textViewSunSetD, textViewPressureD, textViewRhD,textViewVitri,textViewThuNgay
            ,textViewmaxTempDetails,textViewminTempDeatails,textViewSacxuatmua,textViewTamnhin,textViewTuyet;
    private ImageView imageViewIconD,imgback;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        getDatabyId();
        Intent intent = getIntent();
        weather w = (weather) intent.getSerializableExtra("weathers") ;
        textViewDateD.setText(w.getDay());
        textViewStatusD.setText(w.getStatus());
        textViewWindD.setText(w.getWind()+"m/s");
        textViewCloudD.setText(w.getCould()+"%");
        textViewTempD.setText(w.getTemp()+"°C");
        textViewSunRiseD.setText(w.getSunrise());
        textViewSunSetD.setText(w.getSunSet());
        textViewPressureD.setText(w.getPressure()+" hPa");
        textViewRhD.setText(w.getHumidity()+"%");
        Picasso.with(DetailWeather.this).load("https://www.weatherbit.io/static/img/icons/" + w.getImage() + ".png").into(imageViewIconD);
        textViewVitri.setText(w.getVitri());
        textViewThuNgay.setText(w.getThuNgay());
        textViewmaxTempDetails.setText(w.getMaxTemp()+"°C");
        textViewminTempDeatails.setText(w.getMinTemp()+"°C");
        textViewSacxuatmua.setText(w.getSacXuatMua()+"%");
        textViewTamnhin.setText(w.getTamnhin()+"KM");
        textViewTuyet.setText(w.getTuyet()+"mm");
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void getDatabyId() {
        textViewDateD = findViewById(R.id.textViewDayDetail);
        textViewStatusD = findViewById(R.id.textViewStatusDetail);
        imageViewIconD = findViewById(R.id.imageIconDetail);
        textViewWindD = findViewById(R.id.textViewMillDetail);
        textViewCloudD = findViewById(R.id.textViewCloudDetail);
        textViewTempD = findViewById(R.id.textViewTempDetail);
        textViewSunRiseD = findViewById(R.id.textViewSunriseDetail);
        textViewSunSetD = findViewById(R.id.textViewSunsetDetail);
        textViewPressureD = findViewById(R.id.textViewApSuatDetail);
        textViewRhD = findViewById(R.id.textViewHumidityDetail);
        imgback = findViewById(R.id.imageBack);
        textViewVitri = findViewById(R.id.textViewViTri);
        textViewThuNgay = findViewById(R.id.textViewDayDetail);
        textViewmaxTempDetails = findViewById(R.id.textViewMaxTempDetail);
        textViewminTempDeatails = findViewById(R.id.textViewMinTempDetail);
        textViewSacxuatmua = findViewById(R.id.textViewSacXuatMuaDetail);
        textViewTamnhin = findViewById(R.id.textViewTamnhinDetail);
        textViewTuyet = findViewById(R.id.textViewTuyetDetail);
    }
}
