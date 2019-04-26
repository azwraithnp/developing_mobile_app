package com.example.recyclerview.model;

import java.io.Serializable;

public class Food implements Serializable {
    String title;
    String description;
    String ingredients;
    String Procedure;
    String imgPath;

    public Food(String title, String description, String ingredients, String procedure, String imgPath) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        Procedure = procedure;
        this.imgPath = imgPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getProcedure() {
        return Procedure;
    }

    public void setProcedure(String procedure) {
        Procedure = procedure;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
