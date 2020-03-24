package com.antailbaxt3r.petmanagerapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

  EditText emailE, passE, nameE;
  Button signupB, loginB;
  FirebaseAuth auth;
  DatabaseReference userReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);
    attachID();

    loginB.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent login = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
      }
    });

    signupB.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        auth.createUserWithEmailAndPassword(emailE.getText().toString(), passE.getText().toString())
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
              @Override public void onSuccess(AuthResult authResult) {

                userReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(nameE.getText().toString());
                userReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").setValue(emailE.getText().toString());
                Toast.makeText(SignupActivity.this, "User created in!", Toast.LENGTH_SHORT).show();
                Intent main = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(main);
                finish();
              }
            })
            .addOnFailureListener(new OnFailureListener() {
              @Override public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
              }
            });
      }
    });

  }

  private void attachID() {
    emailE = findViewById(R.id.email);
    passE = findViewById(R.id.password);
    nameE = findViewById(R.id.name);
    signupB = findViewById(R.id.sign_up);
    loginB = findViewById(R.id.log_in);
    auth = FirebaseAuth.getInstance();
    userReference = FirebaseDatabase.getInstance().getReference().child("users");
  }
}
