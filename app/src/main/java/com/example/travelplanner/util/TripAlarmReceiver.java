package com.example.travelplanner.util;
import android.content.*;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.travelplanner.R;
public class TripAlarmReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        String tripTitle = intent.getStringExtra("tripTitle");
        String msg = "یادت نره! سفر "" + (tripTitle==null? "":tripTitle) + "" فردا شروع میشه.";
        NotificationCompat.Builder b = new NotificationCompat.Builder(context, "trip_channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("یادآوری سفر")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true);
        NotificationManagerCompat.from(context).notify((int)System.currentTimeMillis(), b.build());
    }
}
