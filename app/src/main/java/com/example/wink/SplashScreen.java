package com.example.wink;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.storage.FirebaseStorage;

public class SplashScreen extends AppCompatActivity {

    //handler for splashscreen
    Handler handler = new Handler();



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //cosmetics for splash screen
        getSupportActionBar().hide();

        //visual of splash screen
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //change the second element to decide to which windows it goest to. (currently its on MapActivity).
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                //the finish function making sure that if you press on the beck button of android you will not go back to the splash screen.
                finish();
            }
        }, 3000);

        //starting the NoteReceiving Foreground service.
        startNoteReceivingFS();

    }


    //// ----------------------
    // was done using this guide: https://www.youtube.com/watch?v=bA7v1Ubjlzw
    //// ----------------------
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startNoteReceivingFS(){
        boolean alreadyExists = false;
        //checking if the service is already running so when we will start the app again it want do another service for the same thing.
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(NotesReceivingService.class.getName().equals(service.service.getClassName())){
                alreadyExists = true;
            }
        }
        //if there is no such service than creat one, otherwise dont do anything.
        if (!alreadyExists){
            Intent serviceIntent = new Intent(SplashScreen.this, NotesReceivingService.class);
            //startForegroundService(serviceIntent);
        }




    }


}