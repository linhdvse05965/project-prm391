package fu.prm391.sxample.project_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CalendarEventActivity extends AppCompatActivity {
    TextView textViewAddEvent;
    ListView listViewCalendar;
    FirebaseFirestore db;
    String nameEvent;
    CalendarAdapter calendarAdapter;
    ArrayList<Calendar> listCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event);
        getDataById();
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String eventName = intent.getStringExtra("event");
      //  Log.i("ketqua",eventName+"");
        listCalendar.add(new Calendar(eventName));
//        db = FirebaseFirestore.getInstance();
//        db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    Intent intent = getIntent();
//                    String date = intent.getStringExtra("date");
//                    String eventName = intent.getStringExtra("event");
//                    Log.i("ketqua",eventName+"");
//                    listCalendar.add(new Calendar(eventName));
//                    QuerySnapshot querySnapshot = task.getResult();
//                    for (QueryDocumentSnapshot doc : querySnapshot) {
//
//                    }
//
//
//                }
//            }
//        });
    }

    private void getDataById() {
        textViewAddEvent = findViewById(R.id.addEvent);
        listViewCalendar = findViewById(R.id.listViewCalendar);
        listCalendar = new ArrayList<Calendar>();
        calendarAdapter = new CalendarAdapter(CalendarEventActivity.this, listCalendar);
        listViewCalendar.setAdapter(calendarAdapter);

    }
}