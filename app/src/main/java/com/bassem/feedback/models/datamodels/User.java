package com.bassem.feedback.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("last_interactions")
    private List<LastInteraction> lastInteractions;
    @SerializedName("avatar")
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LastInteraction> getLastInteractions() {
        return lastInteractions;
    }

    public void setLastInteractions(List<LastInteraction> lastInteractions) {
        this.lastInteractions = lastInteractions;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
