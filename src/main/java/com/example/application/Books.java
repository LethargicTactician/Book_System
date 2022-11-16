package com.example.application;

public class Books extends mainController{
    private int id;
    private String title;
    private String author;
    private int year;
    private int pages;

    public Books(int id, String title, String author, int pages, int year){
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

}
