package com.example.vijay.cloudfirestore;

public class Users {
    String userName, userMobile;

    public Users() {

    }


    public Users(String userName, String userMobile) {
        //this.userId = userId;
        this.userName = userName;
        this.userMobile = userMobile;
    }

   /* public String getUserId() {
        return userId;
    }*/

    public String getUserName() {
        return userName;
    }

    public String getUserMobile() {
        return userMobile;
    }
}
