package fu.prm391.sxample.project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddEventCalendarActivity extends AppCompatActivity {
    EditText editTextAdd;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_calendar);
        getDataById();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String,Object> eventCalendar =  new HashMap<>();
                Intent intent = getIntent();
                String date = intent.getStringExtra("date");
                eventCalendar.put("date",date);
                eventCalendar.put("event",editTextAdd.getText().toString());
                db.collection("events").add(eventCalendar).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        finish();
                    }
                });
            }
        });
    }
    private void getDataById(){
        editTextAdd = findViewById(R.id.editTextAdd);
        buttonAdd = findViewById(R.id.buttonAdd);
    }
}
