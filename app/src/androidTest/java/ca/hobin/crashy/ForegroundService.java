package ca.hobin.crashy;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * User: dhobin
 * Date: 2020-03-10
 */
public class ForegroundService extends Service {

    private static final String STUB_STRING = "stub";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getNotification(this);
        Log.i("Crashy", "This won't run! :O");

        // This will not run due to a crash. It is only here for correctness.
        startForeground(0xDEAD1EAA, getNotification(this));
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager.getNotificationChannel(STUB_STRING) == null) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(STUB_STRING, STUB_STRING, importance);
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private PendingIntent createStartIntent(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    private Notification getNotification(Context context) {
        createChannel(context);
        return new NotificationCompat.Builder(context, STUB_STRING)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(STUB_STRING)
            .setContentText(STUB_STRING)
            .setContentIntent(createStartIntent(context))
            .setOngoing(true)
            .build();
    }

}
