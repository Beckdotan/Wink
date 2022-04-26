package com.example.wink;

public class UploadImage {
    private String imageName;
    private String imagePath;
    private String imageURL;
    private String id;
    private int wasShown;






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
    }

    public UploadImage(int wasShown, String name, String path, String url, String id){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        this.imageName = name;
        this.imagePath = path;
        this.imageURL = url;
        this.wasShown = wasShown;
        this.id = id;
    }

    //Getters

    public String getImageName(){
        return imageName;
    }

    public String getImageUrl(){
        return imageURL;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getId() {
        return id;
    }

    public int getWasShown() {
        return wasShown;
    }

    //Setters
    public void setImageName(String name){
        imageName = name;

    }

    public void setImagePath(String path){
        imagePath = path;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWasShown(int was_shown){
        this.wasShown = was_shown;
    }

    public void print(){


        System.out.println("imageName = " + this.getImageName());
        System.out.println("imagePath = " + this.getImagePath());
        System.out.println("imageURL = " + this.getImageUrl());
        System.out.println("id = " + this.getId());
        System.out.println("wasShown = " + this.getWasShown());


    }

}

