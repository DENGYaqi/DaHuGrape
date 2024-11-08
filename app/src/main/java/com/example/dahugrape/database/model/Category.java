package com.example.dahugrape.database.model;

public class Category {
    Integer id;
    Integer imageUrl;
    String head;

    public Category(Integer id, Integer imageUrl, String head) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.head = head;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
