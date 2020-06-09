package ca.nuro.notificationhistory.AdapterPackage;

public class ItemDataModel {
    private String title;
    private String message;
    private int img;

    /*---------Getters------*/
    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public int getImg() {
        return img;
    }

    /*---------Setters------*/
    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
