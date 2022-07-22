package com.example.squlitedemo.models;

public class Posts {

    private int postNo;
    private String post;
    private String username;
    private String date;
    private boolean approvedStatus;

    public int getPostNo() {
        return postNo;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(boolean approvedStatus) {
        this.approvedStatus = approvedStatus;
    }
}
