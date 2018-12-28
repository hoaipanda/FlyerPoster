package com.design.flyerposter.data;

public class TextJson {
    private int xPos;
    private int yPos;
    private String color;
    private String text;
    private int size;
    private String fontPath;
    private String bg_image;
    private int opacity;
    private String shadowColor;

    public TextJson() {
    }

    public TextJson(int xPos, int yPos, String color, String text, int size, String fontPath, String bg_image, int opacity, String shadowColor) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
        this.text = text;
        this.size = size;
        this.fontPath = fontPath;
        this.bg_image = bg_image;
        this.opacity = opacity;
        this.shadowColor = shadowColor;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }

    public String getBg_image() {
        return bg_image;
    }

    public void setBg_image(String bg_image) {
        this.bg_image = bg_image;
    }

    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    public String getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
    }
}
