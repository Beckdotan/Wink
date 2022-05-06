package com.example.wink;

public class UploadImage {
    private String imageName;
    private String imagePath;
    private String imageURL;
    private String id;
    private int wasShown;
    private String showTimeInMillis;






    public UploadImage() {
        //empty constructor.
    }


    public UploadImage(String name){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        imageName = name;
        wasShown = 0;
    }

    //for users adding new img.
    public UploadImage(String name, String path){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        this.imageName = name;
        this.imagePath = path;
        this.imageURL = "https://storage.googleapis.com/wink-e1b43.appspot.com"+path;
        this.wasShown = 0;
        this.id = "";
        showTimeInMillis = "0";

    }


    public UploadImage(String name, String path,String id){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        this.imageName = name;
        this.imagePath = path;
        this.imageURL = "https://storage.googleapis.com/wink-e1b43.appspot.com"+path;
        this.wasShown = 0;
        this.id = id;
        this.showTimeInMillis = "0";
    }

    public UploadImage(String name, String path,String id, String time){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        this.imageName = name;
        this.imagePath = path;
        this.imageURL = "https://storage.googleapis.com/wink-e1b43.appspot.com"+path;
        this.wasShown = 0;
        this.id = id;
        this.showTimeInMillis = time;
    }

    public UploadImage(int wasShown, String name, String path, String url, String id, String time){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        this.imageName = name;
        this.imagePath = path;
        this.imageURL = url;
        this.wasShown = wasShown;
        this.id = id;
        this.showTimeInMillis = time;
    }

    //Getters

    public String getImageName(){
        return this.imageName;
    }

    public String getImageUrl(){
        return this.imageURL;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public String getId() {
        return this.id;
    }

    public int getWasShown() {
        return this.wasShown;
    }

    public String getShowTimeInMillis() {
        return this.showTimeInMillis;
    }

    //Setters
    public void setImageName(String name){
        this.imageName = name;

    }

    public void setImagePath(String path){
        this.imagePath = path;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWasShown(int was_shown){
        this.wasShown = was_shown;
    }

    public void setShowTimeInMillis(String showTimeInMillis) {
        this.showTimeInMillis = showTimeInMillis;
    }

    public void print(){


        System.out.println("imageName = " + this.getImageName());
        System.out.println("imagePath = " + this.getImagePath());
        System.out.println("imageURL = " + this.getImageUrl());
        System.out.println("id = " + this.getId());
        System.out.println("wasShown = " + this.getWasShown());
        System.out.println("Time In Millis = " + this.getShowTimeInMillis());


    }

}

