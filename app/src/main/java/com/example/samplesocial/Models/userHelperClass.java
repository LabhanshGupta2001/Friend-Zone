package com.example.samplesocial.Models;

public class userHelperClass {
    public String name, mobile, email, password, profile;

    public userHelperClass(String name, String email, String mobile, String password, String profile) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
