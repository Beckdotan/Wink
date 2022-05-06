package com.example.wink;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class NotificationBroadcastReciver extends BroadcastReceiver {

    private static final String title = "Itay sent you a surprise 😱";
    private static final String massage = "Get in to see :)";

    public static String notificationChannel  = "notificationChannel";
    public static int notificationId  = 200;
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context,RecivedNoteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent  pendingIntent = PendingIntent.getActivity(context,0,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notificationChannel)
                .setSmallIcon(R.drawable.wink_icon)
                .setContentTitle(title)
                .setContentText(massage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(notificationId, builder.build() );
    }
}
