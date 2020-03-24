package com.antailbaxt3r.petmanagerapp;

import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SignupActivity extends AppCompatActivity {

  EditText emailE, passE, nameE;
  Button signupB, loginB;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);
    attachID();


  }

  private void attachID() {
    emailE = findViewById(R.id.email);
    passE = findViewById(R.id.password);
    nameE = findViewById(R.id.name);
    signupB = findViewById(R.id.sign_up);
    loginB = findViewById(R.id.log_in);
  }
}
