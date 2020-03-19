package com.antailbaxt3r.petmanagerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetRVAdapter extends RecyclerView.Adapter<PetRVAdapter.PetViewHolder> {

  @NonNull @Override
  public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new PetViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_pet, parent, false));
  }

  @Override public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 0;
  }

  class PetViewHolder extends RecyclerView.ViewHolder{

    TextView name, type, age;

    public PetViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.pet_name);
      age = itemView.findViewById(R.id.pet_age);
      type = itemView.findViewById(R.id.pet_type);
    }
  }
}
