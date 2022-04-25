package com.example.wink;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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




public class MainActivity extends AppCompatActivity {
    Button mPhotoHistory;
    Button mPhotoActivity;


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

        //cosmetics for splash screen
        getSupportActionBar().hide();

        mPhotoHistory = (Button) findViewById(R.id.photoHistory);
        mPhotoActivity = (Button) findViewById(R.id.photo_activity);
        btnShowLocation = (Button) findViewById(R.id.button);

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

}