package com.antailbaxt3r.petmanagerapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

  EditText emailE, passE;
  Button signupB, loginB;
  FirebaseAuth auth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    attachID();

    signupB.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent signUP = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(signUP);
        finish();
      }
    });

    loginB.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        auth.signInWithEmailAndPassword(emailE.getText().toString(), passE.getText().toString())
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
              @Override public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "User logged in!", Toast.LENGTH_SHORT).show();
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);
                finish();
              }
            })
            .addOnFailureListener(new OnFailureListener() {
              @Override public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
              }
            });
      }
    });

  }

  private void attachID() {
    emailE = findViewById(R.id.email);
    passE = findViewById(R.id.password);
    signupB = findViewById(R.id.sign_up);
    loginB = findViewById(R.id.log_in);
    auth = FirebaseAuth.getInstance();
  }
}
