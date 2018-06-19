package com.example.administrator.litepal;



public class Item {
    public String getiId() {
        return iId;
    }

    public void setiId(String iId) {
        this.iId = iId;
    }

    public String getiTitle() {
        return iTitle;
    }

    public void setiTitle(String iTitle) {
        this.iTitle = iTitle;
    }

    public String getiTime() {
        return iTime;
    }

    public void setiTime(String iTime) {
        this.iTime = iTime;
    }
    public Item (String id,String title,String time){
        this.iId = id;
        this.iTitle = title;
        this.iTime = time;
    }

    private String iId;
    private String iTitle;
    private String iTime;
}
