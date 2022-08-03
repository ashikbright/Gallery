package com.ashik.gallery;

public class Upload {
    private String imageUrl;
    private String desc;

    public Upload(){

    }

    public Upload(String imageUrl,String desc) {
        this.imageUrl = imageUrl;
        this.desc=desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
