package com.example.newuserinterface;

public class AppModel {
    private int image;
    private String name;
    private String rights;
    private String summary;
    private String category;
    private String title;

    // Constructor básico
    public AppModel(int image, String name, String rights, String summary) {
        this.image = image;
        this.name = name;
        this.rights = rights;
        this.summary = summary;
    }

    // Constructor alternativo para categorías
    public AppModel(String name, String rights, String summary) {
        this.name = name;
        this.rights = rights;
        this.summary = summary;
        this.category = category;

    }

    // Constructor para títulos con imagen (layouts de portada)
    public AppModel(int image, String title) {
        this.image = image;
        this.title = title;
    }

    // Getters
    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getRights() {
        return rights;
    }

    public String getSummary() {
        return summary;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
