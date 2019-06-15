package com.bnuz.novelreader.model;


public class Book {
    private String imageUrl;
    private String name;
    private String author;
    private String webType;
    private int idx;
    private String url;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWebType() {
        return webType;
    }

    public void setWebType(String webType) {
        this.webType = webType;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", webType='" + webType + '\'' +
                ", idx=" + idx +
                ", url='" + url + '\'' +
                '}';
    }
}