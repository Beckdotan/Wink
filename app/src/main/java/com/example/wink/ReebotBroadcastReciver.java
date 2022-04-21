package com.example.wink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

//// ----------------------
// was done using this guide: https://www.youtube.com/watch?v=bA7v1Ubjlzw
//// ----------------------

public class ReebotBroadcastReciver extends BroadcastReceiver {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent serviceIntent = new Intent(context, NotesReceivingService.class);
            context.startForegroundService(serviceIntent);
        }
    }
}
