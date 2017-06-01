package com.bassem.feedback.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public class LastInteraction {
    @SerializedName("id")
    private String id;
    @SerializedName("date")
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
