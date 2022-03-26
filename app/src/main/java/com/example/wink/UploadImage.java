package com.example.wink;

public class UploadImage {
    private String imageName;
    private String imageUrl;

    public UploadImage() {
        //empty constructor.
    }


    public UploadImage(String name){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        imageName = name;
    }



    public UploadImage(String name, String URL){
        if (name.trim().equals("")){
            name = "NO NAME";
        }
        imageName = name;
        imageUrl = URL;
    }


    //Getters

    public String GetImageName(){
        return imageName;
    }

    public String GetImageUrl(){
        return imageUrl;
    }

    //Setters

    public void SetImageName(String name){
        imageName = name;

    }

    public void SetImageUrl(String URL){
        imageUrl = URL;
    }



}

