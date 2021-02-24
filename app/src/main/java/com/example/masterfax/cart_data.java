package com.example.masterfax;

public class cart_data
{
    String key;
    String description;
    String elegantNumberButton;
    String price;
    String savecurrentdate;
    String savecurrenttime;
    String title;
    String quantity;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public cart_data(){}

    public cart_data(String quantity,String key, String description, String elegantNumberButton, String price, String savecurrentdate, String savecurrenttime, String title) {
        this.key = key;
        this.description = description;
        this.elegantNumberButton = elegantNumberButton;
        this.price = price;
        this.savecurrentdate = savecurrentdate;
        this.savecurrenttime = savecurrenttime;
        this.title = title;
        this.quantity = quantity;


    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getElegantNumberButton() {
        return elegantNumberButton;
    }

    public void setElegantNumberButton(String elegantNumberButton) {
        this.elegantNumberButton = elegantNumberButton;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSavecurrentdate() {
        return savecurrentdate;
    }

    public void setSavecurrentdate(String savecurrentdate) {
        this.savecurrentdate = savecurrentdate;
    }

    public String getSavecurrenttime() {
        return savecurrenttime;
    }

    public void setSavecurrenttime(String savecurrenttime) {
        this.savecurrenttime = savecurrenttime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
