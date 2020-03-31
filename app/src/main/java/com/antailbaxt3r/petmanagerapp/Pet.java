package com.antailbaxt3r.petmanagerapp;

public class Pet {

  private String name, type;
  private int age;

  private String imgURL;

  public Pet() {
  }

  public Pet(String name, String type, int age) {
    this.name = name;
    this.type = type;
    this.age = age;
    imgURL="";
  }

    public Pet(String name, String type, int age, String imgURL) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.imgURL = imgURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
