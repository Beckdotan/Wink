package com.example.wink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HistoryActivityAdapter mAdapter;
    private DatabaseReference mDataBaseRef;
    private List<UploadImage> mUploads;
    SQLiteDBHelper myLocalDB;
    ArrayList<String> imgName;
    ArrayList<byte[]> imgByte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        myLocalDB = new SQLiteDBHelper(this);
        imgName = new ArrayList<>();
        imgByte = new ArrayList<>();
        storeDataInArray();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter= new HistoryActivityAdapter(HistoryActivity.this, imgName, imgByte);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
    }

    //taking the data from the Local DB and store it in the arrays.
    void storeDataInArray(){
        Cursor cursor = myLocalDB.readAllData();
        if (cursor.getCount() == 0 ){
            Toast.makeText(this, "No DATA IM LOCAL DB", Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()){
                imgName.add(cursor.getString(1));
                imgByte.add(cursor.getBlob(3));
            }
        }
    }
}