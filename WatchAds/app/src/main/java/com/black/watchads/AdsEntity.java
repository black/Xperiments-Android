package com.black.watchads;

public class AdsEntity {
    private String imgURL;
    private String title;
    private String description;
    private String hyperLink;

    public AdsEntity(String imgURL, String title, String description, String hyperLink) {
        this.imgURL = imgURL;
        this.title = title;
        this.description = description;
        this.hyperLink = hyperLink;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
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

    public String getHyperLink() {
        return hyperLink;
    }

    public void setHyperLink(String hyperLink) {
        this.hyperLink = hyperLink;
    }
}
