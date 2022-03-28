package com.example.wink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HistoryActivityAdapter mAdapter;
    private DatabaseReference mDataBaseRef;
    private List<UploadImage> mUploads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();
        mDataBaseRef = FirebaseDatabase.getInstance("https://wink-e1b43-default-rtdb.europe-west1.firebasedatabase.app").getReference("Images/");

        mDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapShot : snapshot.getChildren()){
                    UploadImage uploadImage = postSnapShot.getValue(UploadImage.class);
                    mUploads.add(uploadImage);
                }

                mAdapter = new HistoryActivityAdapter(HistoryActivity.this, mUploads);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}