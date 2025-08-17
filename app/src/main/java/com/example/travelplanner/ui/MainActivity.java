package com.example.travelplanner.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.travelplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom;
    private FloatingActionButton fab;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Ensure notification channel exists (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel("trip_channel", "Trip Reminders", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(ch);
        }

        bottom = findViewById(R.id.bottom_nav);
        fab = findViewById(R.id.fab);
        switchFragment(new TripsFragment());
        fab.setOnClickListener(v -> startActivity(new Intent(this, AddTripActivity.class)));
        bottom.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_trips) { fab.show(); switchFragment(new TripsFragment()); return true; }
                else if (item.getItemId() == R.id.nav_todos) { fab.hide(); switchFragment(new TodosFragment()); return true; }
                else if (item.getItemId() == R.id.nav_profile) { fab.hide(); switchFragment(new ProfileFragment()); return true; }
                                else if (item.getItemId() == R.id.nav_itinerary) { fab.hide(); switchFragment(new ItineraryFragment()); return true; }
                return false;
            }
        });
    }
    private void switchFragment(Fragment f){ getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit(); }
}
