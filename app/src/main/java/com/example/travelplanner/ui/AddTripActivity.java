package com.example.travelplanner.ui;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.datepicker.MaterialDatePicker;
import androidx.appcompat.app.AppCompatActivity;
import com.example.travelplanner.R;
import com.example.travelplanner.data.db.*;
import com.example.travelplanner.util.TripAlarmReceiver;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
public class AddTripActivity extends AppCompatActivity implements OnMapReadyCallback {
    private EditText etTitle, etStart, etEnd, etNotes;
    private AppDatabase db;
    private GoogleMap map;
    private long createdTripId = -1;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        db = AppDatabase.get(this);
        etTitle=findViewById(R.id.etTitle); etStart=findViewById(R.id.etStart); etEnd=findViewById(R.id.etEnd); etNotes=findViewById(R.id.etNotes);
        Button btnSave = findViewById(R.id.btnSaveTrip);
        SupportMapFragment mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.map_container, mapFragment).commitNow();
        mapFragment.getMapAsync(this);
        btnSave.setOnClickListener(v -> {
            Trip t = new Trip();
            t.title = etTitle.getText().toString();
            t.startDate = etStart.getText().toString();
            t.endDate = etEnd.getText().toString();
            t.notes = etNotes.getText().toString();
            t.uid = FirebaseAuth.getInstance().getCurrentUser()!=null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : "guest";
            createdTripId = db.dao().insertTrip(t);
            scheduleTripAlarm(t.title, t.startDate);
            finish();
        
    private void setDatePickers(){
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        etStart.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ شروع").build();
            picker.addOnPositiveButtonClickListener(sel -> etStart.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "start");
        });
        etEnd.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ پایان").build();
            picker.addOnPositiveButtonClickListener(sel -> etEnd.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "end");
        });
    }

});
        setDatePickers();
    
    private void setDatePickers(){
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        etStart.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ شروع").build();
            picker.addOnPositiveButtonClickListener(sel -> etStart.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "start");
        });
        etEnd.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ پایان").build();
            picker.addOnPositiveButtonClickListener(sel -> etEnd.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "end");
        });
    }

}
    @Override public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setOnMapClickListener(latLng -> {
            map.addMarker(new MarkerOptions().position(latLng).title("مکان"));
            if (createdTripId == -1) {
                Trip t = new Trip();
                t.title = etTitle.getText().toString().isEmpty() ? "سفر جدید" : etTitle.getText().toString();
                t.startDate = etStart.getText().toString();
                t.endDate = etEnd.getText().toString();
                t.notes = etNotes.getText().toString();
                t.uid = FirebaseAuth.getInstance().getCurrentUser()!=null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : "guest";
                createdTripId = db.dao().insertTrip(t);
            
    private void setDatePickers(){
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        etStart.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ شروع").build();
            picker.addOnPositiveButtonClickListener(sel -> etStart.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "start");
        });
        etEnd.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ پایان").build();
            picker.addOnPositiveButtonClickListener(sel -> etEnd.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "end");
        });
    }

}
            Place p = new Place();
            p.tripId = createdTripId;
            p.lat = latLng.latitude; p.lng = latLng.longitude;
            p.name = "مکان"; p.description = "";
            db.dao().insertPlace(p);
        
    private void setDatePickers(){
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        etStart.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ شروع").build();
            picker.addOnPositiveButtonClickListener(sel -> etStart.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "start");
        });
        etEnd.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ پایان").build();
            picker.addOnPositiveButtonClickListener(sel -> etEnd.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "end");
        });
    }

});
        setDatePickers();
    
    private void setDatePickers(){
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        etStart.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ شروع").build();
            picker.addOnPositiveButtonClickListener(sel -> etStart.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "start");
        });
        etEnd.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ پایان").build();
            picker.addOnPositiveButtonClickListener(sel -> etEnd.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "end");
        });
    }

}
    private void scheduleTripAlarm(String tripTitle, String startDateStr) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            java.util.Date startDate = sdf.parse(startDateStr);
            if (startDate == null) return;
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(java.util.Calendar.DAY_OF_YEAR, -1);
            cal.set(java.util.Calendar.HOUR_OF_DAY, 21); cal.set(java.util.Calendar.MINUTE, 0); cal.set(java.util.Calendar.SECOND, 0);
            Intent intent = new Intent(this, TripAlarmReceiver.class);
            intent.putExtra("tripTitle", tripTitle);
            PendingIntent pi = PendingIntent.getBroadcast(this, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            if (am != null) am.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        
    private void setDatePickers(){
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        etStart.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ شروع").build();
            picker.addOnPositiveButtonClickListener(sel -> etStart.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "start");
        });
        etEnd.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ پایان").build();
            picker.addOnPositiveButtonClickListener(sel -> etEnd.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "end");
        });
    }

} catch (Exception ignored) {
    private void setDatePickers(){
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        etStart.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ شروع").build();
            picker.addOnPositiveButtonClickListener(sel -> etStart.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "start");
        });
        etEnd.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ پایان").build();
            picker.addOnPositiveButtonClickListener(sel -> etEnd.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "end");
        });
    }

}
    
    private void setDatePickers(){
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        etStart.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ شروع").build();
            picker.addOnPositiveButtonClickListener(sel -> etStart.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "start");
        });
        etEnd.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ پایان").build();
            picker.addOnPositiveButtonClickListener(sel -> etEnd.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "end");
        });
    }

}

    private void setDatePickers(){
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        etStart.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ شروع").build();
            picker.addOnPositiveButtonClickListener(sel -> etStart.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "start");
        });
        etEnd.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().setTitleText("تاریخ پایان").build();
            picker.addOnPositiveButtonClickListener(sel -> etEnd.setText(fmt.format(new java.util.Date(sel))));
            picker.show(getSupportFragmentManager(), "end");
        });
    }

}
