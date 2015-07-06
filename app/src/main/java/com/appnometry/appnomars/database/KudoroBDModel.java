package com.appnometry.appnomars.database;

public class KudoroBDModel {
	private String id = "";
	private String price = "";
    private String image_url = "";
    private String title = "";
    private String short_desc = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    @Override
    public String toString() {
        return "KudoroBDModel{" +
                "id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", image_url='" + image_url + '\'' +
                ", title='" + title + '\'' +
                ", short_desc='" + short_desc + '\'' +
                '}';
    }
}
