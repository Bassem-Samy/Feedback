package com.bassem.feedback.models.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public class LastInteraction implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("date")
    private Date date;

    public LastInteraction() {
    }

    protected LastInteraction(Parcel in) {
        id = in.readInt();
    }

    public static final Creator<LastInteraction> CREATOR = new Creator<LastInteraction>() {
        @Override
        public LastInteraction createFromParcel(Parcel in) {
            return new LastInteraction(in);
        }

        @Override
        public LastInteraction[] newArray(int size) {
            return new LastInteraction[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
    }
}
