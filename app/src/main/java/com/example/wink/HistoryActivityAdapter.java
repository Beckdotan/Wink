package com.example.wink;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.nio.ByteBuffer;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivityAdapter extends RecyclerView.Adapter<HistoryActivityAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> imgName;
    private ArrayList<byte[]> imgByte;

    //constructor
    public HistoryActivityAdapter(Context mContext, ArrayList<String> imgName, ArrayList<byte[]> imgByte) {
        this.mContext = mContext;
        this.imgName = imgName;
        this.imgByte = imgByte;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //fore each index wewill show the titel and the image.
        holder.textViewName.setText(String.valueOf(imgName.get(position)));
        try {
            //needs to be in try and catch for some reason.
            holder.imageView.setImageBitmap(byte2Bitmap(imgByte.get(position)) );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return imgName.size();
    }

    //my view holder class
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageView;

        //constructor
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }

    //making the byte[] information as is saved in the DB to bitmap (image).
    public static Bitmap byte2Bitmap(byte[] imgbyte) throws SQLException {
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length);
        return bitmap;
    }
}
