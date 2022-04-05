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

    public UploadImage(String name, String path){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        imageName = name;
        imagePath = path;
        imageURL = "https://storage.googleapis.com/wink-e1b43.appspot.com"+path;
        wasShown = 0;
    }

    public UploadImage(String name, String path,String id){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        imageName = name;
        imagePath = path;
        imageURL = "https://storage.googleapis.com/wink-e1b43.appspot.com"+path;
        wasShown = 0;
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
}

