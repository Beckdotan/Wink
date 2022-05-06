package com.example.wink;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recived_note);

        //cosmetics for splash screen
        getSupportActionBar().hide();


        imageView = findViewById(R.id.imageView);
        myLocalDB = new SQLiteDBHelper(this);

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





                /*
                try {
                    imageView.setImageBitmap(HistoryActivityAdapter.byte2Bitmap(byteImg));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                
                 */

            }


        }







    }
}
