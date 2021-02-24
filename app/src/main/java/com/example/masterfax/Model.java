package com.example.masterfax;

public class Model
{
    String category;
    String imageUrl;
    Long price;
    String title;
    String description;



    public Model(){}

    public Model(String category ,String imageUrl, Long price, String title,String description) {
        this.category = category;
        this.imageUrl = imageUrl;
        this.price = price;
        this.title = title;
        this.description=description;
   }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
