package com.example.androidfirstlinecode.sqllite.litepal;

/**
 * 书籍
 * @author JinXin
 */
public class Book {

    private int id;
    /**
     * 作者
     */
    private String author;
    /**
     * 价格
     */
    private double price;
    /**
     * 页数
     */
    private int pages;
    /**
     * 名称
     */
    private String name;
    /**
     * 出版社
     */
    private String press;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }
}
