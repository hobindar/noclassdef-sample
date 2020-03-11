package ca.hobin.crashy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Example test to demonstrate wrong classpath if running service in Android Test.
 * <p>
 * Toggle {@code PASSING_SCENARIO} to switch between working and non-working case.
 * Only difference between the two is one is proxied through a service.
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final boolean PASSING_SCENARIO = false;
    private static final String STUB_STRING = "stub";

    @Test
    public void useAppContext() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        if (PASSING_SCENARIO) {
            getNotification(targetContext);
        } else {
            Intent intent = new Intent(context, ForegroundService.class);
            ContextCompat.startForegroundService(targetContext, intent);
        }

        assertTrue(true);
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
