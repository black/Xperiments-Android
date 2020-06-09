package com.example.examplelistview;

public class ItemDataModel {
    private String title;
    private String description;
    private int progress;

    public ItemDataModel(String title, String description, int progress) {
        this.title = title;
        this.description = description;
        this.progress = progress;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
