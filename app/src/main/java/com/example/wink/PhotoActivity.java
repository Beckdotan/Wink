package com.example.wink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class PhotoActivity extends AppCompatActivity {


    //using this videos:
    //https://www.youtube.com/watch?v=MfCiiTEwt3g&list=PLrnPJCHvNZuB_7nB5QD-4bNg6tpdEUImQ

    private static final int PICK_IMAGE_REQUEST =1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mImageTitel;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;

    private FirebaseStorage storage;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    public String ImgURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i( "PhotoActivity","created activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //cosmetics for splash screen
        getSupportActionBar().hide();


        mButtonChooseImage = findViewById(R.id.choose_image);
        mButtonUpload = findViewById(R.id.image_upload);
        mImageTitel = findViewById(R.id.image_titel_text);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference("uploads");
     //   mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i( "PhotoActivity","clicked on chooseImageButton");
                //create new intent and preper it to send for opening new activity for choosing the window.
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                Log.i( "PhotoActivity","created intent");
                someActivityResultLauncher.launch(intent);

            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i( "PhotoActivity","clicked on buttonUpload");
                //checking if already uploading. if there is uplading procces keep doing it and dont do any more. else, upload.
                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(PhotoActivity.this, "Upload In Progress", Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });
    }

    //  creating new activity for the image choose window.
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.i( "PhotoActivity","in Result Launcher");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        mImageUri  = data.getData();
                        //Load Image had to be in a function outside of the ActivityResult becouse of the "this" statment"
                        LoadImage();


                    }
                }
            });


    private void LoadImage(){
        Picasso.with(this).load(mImageUri).into(mImageView);
    }

    //getting the file extension (.jpg / .png) and such.
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));


    }
    private void uploadFile(){
        if (mImageUri != null){
            //uploading the image to storage with the name in milliseconds (thats how we use uniq name).
            String imgName = System.currentTimeMillis()+ "." + getFileExtension(mImageUri);
            StorageReference fileReference = mStorageRef.child(imgName);
            //fileReference.putFile(mImageUri);
            Log.i("fileRef path:", fileReference.getPath());

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //Cosmetics - after 5 seconds get the progress bur to 0 again.
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 5000);

                            //for debugging no need for it because after we saving in realtime db we are sending the message.
                            //Toast.makeText(PhotoActivity.this, "Upload was successful", Toast.LENGTH_LONG).show();


                            // --------------    FIRST WAY     -------------

                            /*
                            //- creating gs url: for exsample:
                             //creating the im url
                            StorageReference imgReference = storage.getReference("uploads/"+ imgName);
                             UploadImage upload = new UploadImage(mImageTitel.getText().toString().trim(), imgReference.toString());
                            Log.i("Upload Image", "onSuccess: " + upload.getImageUrl());
                             */

                            // ---------------   SECOND WAY    ---------
                            //creating the real url
                            StorageReference imgReference = storage.getReference("uploads/"+ imgName);

                            //getting the download url
                            imgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.i("getDownloadURL", "onSuccess: Download URL" + uri.toString());
                                    ImgURL = uri.toString();
                                    UploadImage upload = new UploadImage(mImageTitel.getText().toString().trim(), ImgURL);
                                    Log.i("Upload Image", "onSuccess: " + ImgURL);
                                   //saving the Image and matadata to realtime DB
                                    String key = saveImageInDB(upload);

                                    //if in realtime DB:
                                    if (key == "0"){ // if failed in DB
                                        Toast.makeText(PhotoActivity.this, "Upload was NOT successful", Toast.LENGTH_LONG).show();
                                    } else{ //if success in realtime DB
                                        Toast.makeText(PhotoActivity.this, "SAVED IN DB", Toast.LENGTH_LONG).show();

                                        //adding it to local DB:
                                        SQLiteDBHelper myDB = new SQLiteDBHelper(PhotoActivity.this);
                                        myDB.addImg(key, upload);

                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PhotoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();


                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });

        }else{
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_LONG).show();

        }
    }

    //sending the img to DB.
    //if works - return the automatic key was given and send toast to user
    //else return "0" as didnt happen.  and send toast to user.
    public String saveImageInDB(UploadImage img) {

        // Write a message to the database
        Log.i("TAG", "saveImageInDB: ");
        try {
            //getting refrense to firebase realtime db.
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://wink-e1b43-default-rtdb.europe-west1.firebasedatabase.app");
            DatabaseReference myRef = database.getReference("Images/").push();
            //getting the new key from DB
            String key = myRef.getKey();

            //sending img to DB
            myRef.setValue(img);
            Log.i("saveNoteInDB", "saved in DB! :)");

            /// !!!!!!!!!!!! ----- Can add the note id to the user notes list here ----- !!!!!!!!!

            return key;
        } catch (Error e) {
            Log.e("saveNoteInDB", "didn't work");
            return "0";
        }

    }




}