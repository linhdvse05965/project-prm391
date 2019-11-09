package fu.prm391.sxample.project_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarAdapter extends BaseAdapter {
    Context context;
    ArrayList<Calendar> arrayListCalendar;

    public CalendarAdapter(Context context, ArrayList<Calendar> arrayListCalendar) {
        this.context = context;
        this.arrayListCalendar = arrayListCalendar;
    }

    @Override
    public int getCount() {
        return arrayListCalendar.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListCalendar.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.eventcalendarlistview,null);
        Calendar c = arrayListCalendar.get(i);
        TextView textViewEvent = view.findViewById(R.id.textViewEvent);
        textViewEvent.setText(c.getNameEvent());
        return view;
    }
}
