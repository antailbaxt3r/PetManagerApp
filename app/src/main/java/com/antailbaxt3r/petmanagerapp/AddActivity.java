package com.antailbaxt3r.petmanagerapp;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class AddActivity extends AppCompatActivity {

  Button addButton;
  EditText nameE, ageE, typeE;
  int arraySize;
  DatabaseReference petReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pets");

  TextView imgSelect;

  Image image;
  String imgPath;
  boolean imgflag=false;

  StorageReference storageReference;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add);
    attachID();

    imgSelect.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ImagePicker.create(AddActivity.this)
                    .single()
                    .folderMode(true)
                    .start();
        }
    });

    addButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
          if(!imgflag){
              // No image selected
              addPet(nameE.getText().toString(), Integer.parseInt(ageE.getText().toString()), typeE.getText().toString());
          } else {
                storageReference=FirebaseStorage.getInstance().getReference();
                 final StorageReference imgRef = storageReference.child("images");
                  Uri file = Uri.fromFile(new File(imgPath));

                  StorageReference newImgRef = imgRef.child(image.getName());

                  newImgRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          Toast.makeText(AddActivity.this,"Upload succesfull",Toast.LENGTH_SHORT).show();
                          newImgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                              @Override
                              public void onSuccess(Uri uri) {
                                  System.out.println(">>>>>URL "+uri);
                                  addPet(nameE.getText().toString(), Integer.parseInt(ageE.getText().toString()),
                                          typeE.getText().toString(),uri.toString());

                              }
                          });
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(AddActivity.this,"Upload failed",Toast.LENGTH_SHORT).show();
                      }
                  });

          }
        }
    });
  }

  private void addPet(String name, int age, String type) {
    Pet pet = new Pet(name, type, age);
    petReference.child("pet" + arraySize).setValue(pet);
  }

  private void addPet(String name, int age, String type, String url){
      Pet pet = new Pet(name, type, age,url);
      petReference.child("pet" + arraySize).setValue(pet);
  }

  private void attachID() {
    addButton = findViewById(R.id.add_pet_button);
    nameE = findViewById(R.id.pet_name);
    ageE = findViewById(R.id.pet_age);
    typeE = findViewById(R.id.pet_type);
    imgSelect=findViewById(R.id.imgTxt);

    arraySize = getIntent().getIntExtra("size", 1);
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
