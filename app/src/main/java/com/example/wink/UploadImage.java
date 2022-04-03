package com.example.wink;

public class UploadImage {
    private String imageName;
    private String imageUrl;
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

    public UploadImage(String name, String URL){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        imageName = name;
        imageUrl = URL;
        wasShown = 0;
    }

    public UploadImage(String name, String URL,String id){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        imageName = name;
        imageUrl = URL;
        wasShown = 0;
        this.id = id;
    }

    //Getters

    public String getImageName(){
        return imageName;
    }

    public String getImageUrl(){
        return imageUrl;
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

    public void setImageUrl(String URL){
        imageUrl = URL;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWasShown(int was_shown){
        this.wasShown = was_shown;
    }
}

