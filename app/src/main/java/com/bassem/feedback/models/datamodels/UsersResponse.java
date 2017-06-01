package com.bassem.feedback.models.datamodels;

import com.bassem.feedback.models.UserFeedbackInfoItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public class UsersResponse {
    @SerializedName("users")
    private List<UserFeedbackInfoItem> users;

    public List<UserFeedbackInfoItem> getUsers() {
        return users;
    }

    public void setUsers(List<UserFeedbackInfoItem> users) {
        this.users = users;
    }
}
