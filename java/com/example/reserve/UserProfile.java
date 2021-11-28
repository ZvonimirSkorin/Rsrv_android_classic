package com.example.reserve;

import android.widget.Button;
import android.widget.ImageView;

public class UserProfile {
    public String userNumber;
    public String userEmail;
    public String userName;
    private ImageView updateProfilePic;
    private Button save;

    public UserProfile(){

    }

    public UserProfile(String userNumber, String userEmail, String userName) {
        this.userNumber = userNumber;
        this.userEmail = userEmail;
        this.userName = userName;

    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
