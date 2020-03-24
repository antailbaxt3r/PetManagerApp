package com.antailbaxt3r.petmanagerapp;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

  Button addButton;
  EditText nameE, ageE, typeE;
  int arraySize;
  DatabaseReference petReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pets");

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add);
    attachID();

    addButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        addPet(nameE.getText().toString(), Integer.parseInt(ageE.getText().toString()), typeE.getText().toString());
      }
    });
  }

  private void addPet(String name, int age, String type) {
    Pet pet = new Pet(name, type, age);
    petReference.child("pet" + arraySize).setValue(pet);
  }

  private void attachID() {
    addButton = findViewById(R.id.add_pet_button);
    nameE = findViewById(R.id.pet_name);
    ageE = findViewById(R.id.pet_age);
    typeE = findViewById(R.id.pet_type);
    arraySize = getIntent().getIntExtra("size", 1);
  }
}
