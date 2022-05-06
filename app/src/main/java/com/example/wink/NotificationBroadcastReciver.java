package com.example.wink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class NotificationBroadcastReciver extends BroadcastReceiver {

    private static final String title = "Itay sent you a surprise 😱";
    private static final String massage = "Get in to see :)";

    public static String notificationChannel  = "notificationChannel";
    public static int notificationId  = 200;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notificationChannel)
                .setSmallIcon(R.drawable.wink_icon)
                .setContentTitle(title)
                .setContentText(massage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(notificationId, builder.build() );
    }
}
