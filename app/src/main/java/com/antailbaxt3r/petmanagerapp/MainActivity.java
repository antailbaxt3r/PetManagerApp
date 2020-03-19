package com.antailbaxt3r.petmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

  RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    attachID();


  }

  private void attachID() {
    recyclerView = findViewById(R.id.pets_rv);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }
}
