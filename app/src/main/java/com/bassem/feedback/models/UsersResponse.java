package com.bassem.feedback.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public class UsersResponse {
    @SerializedName("users")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
