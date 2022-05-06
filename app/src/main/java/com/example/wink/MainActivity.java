package com.example.wink;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button mPhotoHistory;
    Button mPhotoActivity;
    Button  mNotificationButton;


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

        mPhotoHistory = (Button) findViewById(R.id.photoHistory);
        mPhotoActivity = (Button) findViewById(R.id.photo_activity);
        btnShowLocation = (Button) findViewById(R.id.button);
        mNotificationButton = (Button) findViewById(R.id.notification_button);


        mNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // alarm manager was made using : https://www.youtube.com/watch?v=nl-dheVpt8o
                /*
                TODO: adding the time and the actual Text, add id value set a click function to take me to the correct messege.  and sync it to the DB everytime that new messege comse.

                 */
                Toast.makeText(MainActivity.this, "button pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NotificationBroadcastReciver.class);
                PendingIntent  pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);


                AlarmManager alarmManager  = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();
                long  tenSecondsInMillis = 1000* 10;

                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        timeAtButtonClick+tenSecondsInMillis,
                        pendingIntent);



            }
        });

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


        //for location:
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDeviceLocation();
                Log.i(TAG, "onClick: we have permission " + mLastKnownLocation);
            }
        });


        /*
        //check for new things in DB.

        final ArrayList<UploadImage> notes = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://wink-e1b43-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Images");

        //getting lists of Notes and making marker from each one of them and present the markers.
        //setting up listener
        ///preparing list from all relevant notes from servers in a list

        //getting refrense to firebase realtime db.
        Log.e("Main Activity ", "Notes Reciving service is running ....  ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Service", "ON DATA CHANGE  ");
                notes.clear();
                Log.e("Service", "ON DATA CHANGE after clear ");

                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    Log.e("Service", "ON DATA CHANGE in for  ");
                    try {
                        String name = noteSnapshot.child("imageName").getValue().toString();
                        Log.e(TAG, "onDataChange: name = " +  name );
                        String path = noteSnapshot.child("imagePath").getValue().toString();
                        Log.e(TAG, "onDataChange: path = " + path );
                        String id = noteSnapshot.child("id").getValue().toString();
                        Log.e(TAG, "onDataChange: id = " +  id );
                        UploadImage currentNote =new UploadImage(name, path, id);
                        Log.i(TAG, "onDataChange: " + noteSnapshot.getValue().toString());
                        notes.add(currentNote);
                        Log.e("onDataChange", "added" + currentNote.getId());
                    } catch (Exception e) { //might happen if we dont add proper notes to the DB.
                        Log.e("DB to UploadImg", "something went wrong with converting DB to Notes" + e);
                    }
                }
                //adding markers from a list to the map.
                try {
                    for (UploadImage note : notes) {
                        Log.i("note list", "now checking " + note.getId());

                        SQLiteDBHelper myDB = new SQLiteDBHelper(MainActivity.this);
                        //TODO: !!!!! check if its fore this user as well after implementing users verification
                        if (!myDB.isInLocalDB(note.getId())) {
                            Log.e("Note Receive Service", "This not " + note.getId() + "is not in DB");
                            myDB.addImg(note.getId(), note);
                        }
                    }
                } catch (Exception e) {
                    Log.e("On Data Change", "error", e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, "onCancelled: error reading from firebase");
            }
        });



         */






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
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "winkNotificationChannel";
            String discription = "Channel for Wink Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NotificationBroadcastReciver.notificationChannel, name, importance);
            channel.setDescription(discription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }



}