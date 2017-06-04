package com.bassem.feedback.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.bassem.feedback.models.datamodels.LastInteraction;
import com.bassem.feedback.models.datamodels.User;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Bassem Samy on 6/1/2017.
 * A model that extends User class, it contains extra properties and methods required for the business logic
 */

public class UserFeedbackInfoItem extends User implements Comparable<UserFeedbackInfoItem>, Parcelable {

    private String title;
    private UserFeedbackInfoItemType type = UserFeedbackInfoItemType.RECORD;
    private String lastFeedbackSent;
    private long lastFeedbackTimeDifference;


    public UserFeedbackInfoItem() {
        super();
    }

    protected UserFeedbackInfoItem(Parcel in) {
        super(in);
    }

    @Override
    public int compareTo(UserFeedbackInfoItem o) {
        if (this.getLastFeedbackTimeDifference() == o.getLastFeedbackTimeDifference()) {
            return 0;
        } else {
            return this.getLastFeedbackTimeDifference() > o.getLastFeedbackTimeDifference() ? -1 : 1;//desc
        }
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserFeedbackInfoItemType getType() {

        return type;
    }


    public void setType(UserFeedbackInfoItemType type) {
        this.type = type;
    }


    public String getLastFeedbackSent() {
        return lastFeedbackSent;
    }

    public void setLastFeedbackSent(String lastFeedbackSent) {
        this.lastFeedbackSent = lastFeedbackSent;
    }


    public long getLastFeedbackTimeDifference() {
        // if not calculated yet;
        if (lastFeedbackTimeDifference == 0) {
            calculateLastFeedbackTimeDifference();
        }
        return lastFeedbackTimeDifference;
    }

    public void setLastFeedbackTimeDifference(long lastFeedbackTimeDifference) {
        this.lastFeedbackTimeDifference = lastFeedbackTimeDifference;
    }

    /**
     * gets the latest interaction date and return the difference to current time
     *
     * @return time difference
     */
    private void calculateLastFeedbackTimeDifference() {
        if (getLastInteractions() != null && getLastInteractions().size() > 0 && getLastInteractions().get(0).getDate() != null) {

            DateTime now = new DateTime();
            getLastInteractions().get(0).prepareTimeDifference(now);
            DateTime lastInteraction = new DateTime(getLastInteractions().get(0).getDate().getTime());
            setLastFeedbackTimeDifference(now.getMillis() - lastInteraction.getMillis());

        } else {
            setLastFeedbackTimeDifference(Long.MAX_VALUE);
        }
    }


    public void updateInteraction(Date date) {
        if (this.getLastInteractions() == null) {
            ArrayList<LastInteraction> interactions = new ArrayList<>();
        }
        LastInteractionInfoItem interaction = new LastInteractionInfoItem();
        interaction.setId(this.getLastInteractions().size() + 1);
        interaction.setDate(date);
        this.getLastInteractions().add(0, interaction);
        calculateLastFeedbackTimeDifference();

    }

    public LastInteractionInfoItem getlastInteractionItem() {
        if (getLastInteractions() != null && getLastInteractions().size() > 0) {
            return getLastInteractions().get(0);
        }
        return null;
    }
}
