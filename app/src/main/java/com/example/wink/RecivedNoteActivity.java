package com.example.wink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class RecivedNoteActivity extends AppCompatActivity {

    private static final String TAG = "RecivedNoteActivity";
    public static LinkedList<LinkedListNode> q = new LinkedList<LinkedListNode>();
    private ImageView imageView;
    SQLiteDBHelper myLocalDB;
    private Button xButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recived_note);

        //cosmetics for splash screen
        getSupportActionBar().hide();

        xButton = (Button) findViewById(R.id.x_button);
        imageView = findViewById(R.id.imageView);
        myLocalDB = new SQLiteDBHelper(this);



        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecivedNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




        Cursor cursor = myLocalDB.readAllData();
        Log.i(TAG, "onCreate: " + q.toString());


        if (cursor.getCount() == 0 ){
            Toast.makeText(this, "SOMTING IS WRONG - No DATA IM LOCAL DB", Toast.LENGTH_SHORT).show();
        }else {
            if (!q.isEmpty()) {

                cursor.moveToNext();
                int count = 0;
                while((cursor.getString(0) != q.peek().getId()) && cursor.moveToNext() ){
                    count ++;
                }
                byte[] byteImg;
                try{
                    byteImg  = cursor.getBlob(3);
                }catch (Exception e){
                    cursor.moveToPrevious();
                    byteImg = cursor.getBlob(3);
                }
                q.remove(0);
                if (byteImg == null){
                    Log.i(TAG, "onCreate: ITS NULL");
                }
                Bitmap bitmap;
                try {
                    bitmap = HistoryActivityAdapter.byte2Bitmap(byteImg);
                    Log.i(TAG, "onCreate: created image");
                    imageView.setImageBitmap(bitmap);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }


        }







    }
}
