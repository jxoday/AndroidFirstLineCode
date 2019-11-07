package com.example.androidfirstlinecode.widget.recycleview;

/**
 * @author Administrator
 */
public class Fruit {

    private String name;

    private int imageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }
}
