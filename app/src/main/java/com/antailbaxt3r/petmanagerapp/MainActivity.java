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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  RecyclerView recyclerView;
  Button addButton;
  DatabaseReference petReference = FirebaseDatabase.getInstance().getReference().child("pets");
  int arraySize;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    attachID();
    arraySize = 1;

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
        for(DataSnapshot shot : dataSnapshot.getChildren()){
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

  private void attachID() {
    recyclerView = findViewById(R.id.pets_rv);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    addButton = findViewById(R.id.add_pet_button);
  }
}
