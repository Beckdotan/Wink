package com.example.wink;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.location.FusedLocationProviderClient;


public class MainActivity extends AppCompatActivity {
    Button mPhotoHistory;
    Button mPhotoActivity;
    Button  mNotificationButton;
    Button  mRecivedNote;


    //for location
    private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1;
    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private boolean mLocationPermissionGranted;
    private static final int LOCATION_PERMISSION_CODE = 101;
    private Location mLastKnownLocation;
    private FusedLocationProviderClient mfusedLocationProviderClient;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        //cosmetics for splash screen
        getSupportActionBar().hide();


        mPhotoHistory = (Button) findViewById(R.id.another_one_button);
        mPhotoActivity = (Button) findViewById(R.id.done_button);
        //btnShowLocation = (Button) findViewById(R.id.button);
        //mNotificationButton = (Button) findViewById(R.id.notification_button);
        mRecivedNote = (Button) findViewById(R.id.received_note_button);

        mRecivedNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecivedNoteActivity.class);
                startActivity(intent);
            }
        });

        /*
        mNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // alarm manager was made using : https://www.youtube.com/watch?v=nl-dheVpt8o

                TODO: adding the time and the actual Text, add id value set a click function to take me to the correct messege.  and sync it to the DB everytime that new messege comse.


                Toast.makeText(MainActivity.this, "button pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NotificationBroadcastReciver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);


                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();
                long tenSecondsInMillis = 1000 * 10;

                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        timeAtButtonClick + tenSecondsInMillis,
                        pendingIntent);


            }
        });

         */



        mPhotoHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });


        mPhotoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
                startActivity(intent);
            }
        });


        /*
        // LOCATION REALTED

        //for location:
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDeviceLocation();
                Log.i(TAG, "onClick: we have permission " + mLastKnownLocation);
            }
        });



    }


    public void OnRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSION_REQUEST_ACCESS_FINE_LOCATION: {
                //If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        //action to do?
    }


    //for location
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getDeviceLocation() {
        try {
            getLocationPermission();
            Log.i(TAG, "getDeviceLocation " + mLocationPermissionGranted);
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Task locationResult = mfusedLocationProviderClient.getLastLocation();
                    locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                mLastKnownLocation = (Location) task.getResult();
                                Log.i("My Location", mLastKnownLocation.toString());
                            }else{
                                Log.i("nullLocation", "Current location is null");
                                Log.e("nullLocationException", task.getException().toString());

                            }
                        }
                    });
                }

            }
        }catch (SecurityException e){
            Log.e("Exception : $s", e.getMessage());
        }
        */
    }






    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "winkNotificationChannel";
            String discription = "Channel for Wink Notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(NotificationBroadcastReciver.notificationChannel, name, importance);
            channel.setDescription(discription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }



}