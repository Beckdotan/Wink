package com.example.wink;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

//// ----------------------
// was done using this guide: https://www.youtube.com/watch?v=bA7v1Ubjlzw
//// ----------------------

public class NotesReceivingService extends Service {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
                // TODO: change the Runnable function to somthing useful. 
                new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            Log.e("Service", "Notes Reciving service is running ....  ");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();

        final String CHANNEL_ID = "Notes Receiving FS ID";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_LOW
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentText("Note Reciving service working")
                .setContentTitle("Service enabled")
                .setSmallIcon(R.drawable.ic_launcher_background);



        startForeground(1001, notification.build());


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
