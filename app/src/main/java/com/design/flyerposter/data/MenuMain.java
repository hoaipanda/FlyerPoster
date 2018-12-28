package com.design.flyerposter.data;

import android.widget.ImageView;

public class MenuMain {
    private String image;
    private String name;

    public MenuMain() {
    }

    public MenuMain(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
