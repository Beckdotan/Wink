package com.example.wink;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryActivityAdapter extends RecyclerView.Adapter<HistoryActivityAdapter.ImageViewHolder> {

    private Context mContext;
    private List<UploadImage> mUploads;
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    private  StorageReference storageRef = storage.getReference();

    public HistoryActivityAdapter(Context context, List<UploadImage> uploads){
        mContext = context;
        mUploads = uploads;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        UploadImage uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getImageName());
        //StorageReference gsReference = storage.getReferenceFromUrl(uploadCurrent.getImageUrl());

        // ---------------- WAY 1 USING GLIDE   -------------//
        //Glie is open source code for firebase. being used in firebase documentation.
        Glide.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .into(holder.imageView);

        /*
        // ----------------------------    SECOND WAY - USING PICASSO    ----------- //
        // working perfectly as well. picasso was used to present the photo from the phone to the screen.
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerInside()
                .into(holder.imageView);
         */
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

        }
    }
}
