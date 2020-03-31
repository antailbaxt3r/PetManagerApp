package com.antailbaxt3r.petmanagerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PetRVAdapter extends RecyclerView.Adapter<PetRVAdapter.PetViewHolder> {

  private List<Pet> petList = new ArrayList<>();
  private Context context;

  public PetRVAdapter(List<Pet> petList, Context context) {
    this.petList = petList;
    this.context = context;
  }

  @NonNull @Override
  public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new PetViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_pet, parent, false));
  }

  @Override public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
    holder.populate(petList.get(position));
  }

  @Override public int getItemCount() {
    return petList.size();
  }

  class PetViewHolder extends RecyclerView.ViewHolder{

    TextView name, type, age;
    ImageView image;

    public PetViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.pet_name);
      age = itemView.findViewById(R.id.pet_age);
      type = itemView.findViewById(R.id.pet_type);
      image=itemView.findViewById(R.id.pet_image);
    }

    public void populate(Pet pet){
      name.setText(pet.getName());
      age.setText(String.valueOf(pet.getAge()));
      type.setText(pet.getType());

      //pet.getURL()
        if(pet.getImgURL()!=null && (!pet.getImgURL().equals(""))){
            Glide.with(context).load(pet.getImgURL()).into(image);
        }
    }
  }
}
