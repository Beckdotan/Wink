package com.example.wink;

import static android.content.ContentValues.TAG;
import static com.example.wink.SQLiteDBHelper.TABLE_NAME;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//// ----------------------
// was done using this guide: https://www.youtube.com/watch?v=bA7v1Ubjlzw
//// ----------------------

public class NotesReceivingService extends Service {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*
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


         */


        //TODO: instead of using the On data change we should maybe just
        // check every few seconds with runneable if there is anything new
        // by getting all keys from firebase realtime DB, than save it in array
        // and than check if we have it all in the DB, if we dont than we will check this
        // ID's and if its for us we will download it and save the info to the Local DB

                //hardcoded marker for tests
        /*


        ArrayList<UploadImage> notes = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://wink-e1b43-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Images/").push();

                //getting lists of Notes and making marker from each one of them and present the markers.
                //setting up listener
                ///preparing list from all relevant notes from servers in a list

                //getting refrense to firebase realtime db.

        Log.e("Service", "Notes Reciving service is running ....  ");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("Service", "ON DATA CHANGE  ");
                        notes.clear();
                        Log.e("Service", "ON DATA CHANGE after clear ");

                        for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                            Log.e("Service", "ON DATA CHANGE in for  ");
                            try {
                                UploadImage currentNote = noteSnapshot.getValue(UploadImage.class);
                                notes.add(currentNote);
                                Log.e("onDataChange", "added" + currentNote.getId());
                            } catch (Exception e) { //might happen if we dont add proper notes to the DB.
                                Log.e("DB to Markers", "something went wrong with converting DB to Notes" + e);
                            }
                        }
                        //adding markers from a list to the map.
                        try {
                            for (UploadImage note : notes) {
                                Log.i("note list", "now checking " + note.getId());

                                SQLiteDBHelper myDB = new SQLiteDBHelper(NotesReceivingService.this);
                                //TODO: !!!!! check if its fore this user as well after implementing users verification
                                if (!myDB.isInLocalDB(note.getId())) {
                                    Log.e("Note Receive Service", "This not " + note.getId() + "is not in DB");
                                    myDB.addImg(note.getId(), note);
                                }
                            }
                        } catch (Exception e) {
                            Log.e("range", "LastLocation = null", e);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                final String CHANNEL_ID = "Notes Receiving FS ID";
                NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_LOW
                );


         */
//check for new things in DB.

        final ArrayList<UploadImage> notes = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://wink-e1b43-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Images");

        //getting lists of Notes and making marker from each one of them and present the markers.
        //setting up listener
        ///preparing list from all relevant notes from servers in a list

        //getting refrense to firebase realtime db.
        Log.e("Service ", "Notes Reciving service is running ....  ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Service", "ON DATA CHANGE  ");
                notes.clear();
                Log.e("Service", "ON DATA CHANGE after clear ");

                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {

                    Log.e("Service", "ON DATA CHANGE in for  " + noteSnapshot.child("id").getValue().toString() );
                    try {
                        String name = noteSnapshot.child("imageName").getValue().toString();
                        String path = noteSnapshot.child("imagePath").getValue().toString();
                        String id = noteSnapshot.child("id").getValue().toString();
                        String showTime = noteSnapshot.child("showTimeInMillis").getValue().toString();



                        /*
                        //for debugging
                        Log.i(TAG, "onDataChange: path = " + path );
                        Log.i(TAG, "onDataChange: id = " +  id );
                        Log.i(TAG, "onDataChange: name = " +  name );
                        //UploadImage currentNote =new UploadImage(name, path, id, "0");
                         */

                        UploadImage currentNote =new UploadImage(name, path, id, showTime);

                        Log.i(TAG, "onDataChange: " + noteSnapshot.getValue().toString());
                        notes.add(currentNote);
                        Log.i("onDataChange", "added " + currentNote.getId());
                    } catch (Exception e) { //might happen if we dont add proper notes to the DB.
                        Log.i("DB to UploadImg", "something went wrong with converting DB to Notes" + e);
                    }
                }
                //adding note from a list to the local DB.
                try {
                    for (UploadImage note : notes) {
                        Log.i("note list", "now checking " + note.getId());

                        SQLiteDBHelper myDB = new SQLiteDBHelper(NotesReceivingService.this);
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
