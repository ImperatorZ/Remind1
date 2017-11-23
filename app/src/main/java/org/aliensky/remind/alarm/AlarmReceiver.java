package org.aliensky.remind.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import org.aliensky.remind.MainActivity;
import org.aliensky.remind.MyApplication;
import org.aliensky.remind.R;


/**
 * Created by Vitaly on 06.09.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("title");
        long timeStamp = intent.getLongExtra("time_stamp", 0);
        int color = intent.getIntExtra("color", 0);

        Intent resultIntent = new Intent(context, MainActivity.class);

        if (MyApplication.isActivityVisible()) {
            resultIntent = intent;
        }

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) timeStamp,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Reminder");
        builder.setContentText(title);
        builder.setColor(context.getResources().getColor(color));
        builder.setSmallIcon(R.drawable.ic_check_white_48dp);
        builder.setSound(alarmSound);

        builder.setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) timeStamp, notification);

    }
}
