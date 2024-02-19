package com.example.bloodbond;

public class DashB {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageview() {
        return imageview;
    }

    public void setImageview(int imageview) {
        this.imageview = imageview;
    }

    public DashB(String name, int imageview) {
        this.name = name;
        this.imageview = imageview;
    }

    private String name;
    private int imageview;
}