package fu.prm391.sxample.project_android;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {
    //private Activity activity;
   // private int layout;
    Context context;
    ArrayList<weather> arrayList;

    public WeatherAdapter(Context context, ArrayList<weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    //    public WeatherAdapter(Activity activity, int layout, ArrayList<weather> arrayList) {
//        this.activity = activity;
//        this.layout = layout;
//        this.arrayList = arrayList;
//    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.daylistview,null);
        weather w = arrayList.get(i);
        TextView textViewDay = (TextView) view.findViewById(R.id.textViewNgay);
        TextView textViewStatus = (TextView) view.findViewById(R.id.textViewTrangThai);
        TextView textViewMaxTemp = (TextView) view.findViewById(R.id.textViewMaxTemp);
        TextView textViewMinTemp = (TextView) view.findViewById(R.id.textViewMinTemp);
        ImageView imageViewStatus = (ImageView) view.findViewById(R.id.imageViewTrangThai);

        textViewDay.setText(w.getDay());
        textViewStatus.setText(w.getStatus());
        textViewMaxTemp.setText(w.getMaxTemp() + "°C");
        textViewMinTemp.setText(w.getMinTemp() + "°C");
        Picasso.with(context).load("https://www.weatherbit.io/static/img/icons/" + w.getImage() + ".png").into(imageViewStatus);
        return view;
    }
}
