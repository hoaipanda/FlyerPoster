package com.design.flyerposter.data;

public class ImageJson {
    private String image;
    private int heigh;
    private int width;
    private int top;
    private int left;

    public ImageJson() {
    }

    public ImageJson(String image, int heigh, int width, int top, int left) {
        this.image = image;
        this.heigh = heigh;
        this.width = width;
        this.top = top;
        this.left = left;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getHeigh() {
        return heigh;
    }

    public void setHeigh(int heigh) {
        this.heigh = heigh;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }
}
