package com.antailbaxt3r.petmanagerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class UploadActivity extends AppCompatActivity {

    StorageReference storageReference;

    FirebaseUser user;

    Image image;
    String imgPath;
    boolean imgflag=false;

    TextView imgSelect;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imgSelect=findViewById(R.id.imageSelect);
        upload=findViewById(R.id.uploadButton);

        imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.create(UploadActivity.this)
                        .single()
                        .folderMode(true)
                        .start();
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference imgRef = storageReference.child("images");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!imgflag){
                    Toast.makeText(UploadActivity.this,"No image selected",Toast.LENGTH_SHORT).show();
                } else {
                    Uri file = Uri.fromFile(new File(imgPath));
                    StorageReference newImgRef = imgRef.child(image.getName());

                    newImgRef.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(UploadActivity.this,"Upload Succesful",Toast.LENGTH_SHORT).show();
                                    newImgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            System.out.println(">>>>>>>>>"+uri.toString());
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadActivity.this,"Upload failed",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            image = ImagePicker.getFirstImageOrNull(data);
            imgPath=image.getPath();
            imgSelect.setText(imgPath);
            imgflag=true;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
