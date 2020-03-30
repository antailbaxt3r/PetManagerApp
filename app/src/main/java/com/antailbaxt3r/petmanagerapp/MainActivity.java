package com.antailbaxt3r.petmanagerapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  RecyclerView recyclerView;
  Button addButton;
  Button openUpload;
  FirebaseAuth auth;
  DatabaseReference petReference;
  int arraySize;

  @Override protected void onStart() {
    super.onStart();

    if(FirebaseAuth.getInstance().getCurrentUser() == null){
      Intent login = new Intent(MainActivity.this, LoginActivity.class);
      startActivity(login);
      finish();
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    attachID();
    arraySize = 1;

    openUpload.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent uploadActivity = new Intent(MainActivity.this,UploadActivity.class);
        startActivity(uploadActivity);
      }
    });

    if(FirebaseAuth.getInstance().getCurrentUser() != null) {
      FirebaseUser user = auth.getCurrentUser();
      String uid = user.getUid();

      petReference =
          FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("pets");

      addButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
          addIntent.putExtra("size", arraySize);
          startActivity(addIntent);
        }
      });

      petReference.addValueEventListener(new ValueEventListener() {
        @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
          ArrayList<Pet> petList = new ArrayList<>();
          for (DataSnapshot shot : dataSnapshot.getChildren()) {
            petList.add(shot.getValue(Pet.class));
            Log.i("PET NAME", shot.getValue(Pet.class).getName());
            arraySize++;
          }

          recyclerView.setAdapter(new PetRVAdapter(petList, getApplicationContext()));
        }

        @Override public void onCancelled(@NonNull DatabaseError databaseError) {

        }
      });
    }
  }

  private void attachID() {
    recyclerView = findViewById(R.id.pets_rv);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    addButton = findViewById(R.id.add_pet_button);
    openUpload=findViewById(R.id.openUpload);
    auth = FirebaseAuth.getInstance();
  }
}
