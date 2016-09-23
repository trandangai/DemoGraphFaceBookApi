package com.khtn.demographfacebookapi;

/**
 * Created by CuongLe on 9/23/2016.
 */
public class MyPost {
    private String story;
    private String created_time;
    private String id;
    private String picture;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public MyPost(String story, String picture, String id, String created_time) {
        this.story = story;
        this.picture = picture;
        this.id = id;
        this.created_time = created_time;
    }

    public MyPost() {

    }

    @Override
    public String toString() {
        return "- Message : "+message+"\n"+"- Story : "+story+"\n"+" - Created time : "+created_time+"\n"+" - ID : "+id+"\n"+" - Picture : "+picture;
    }


}
