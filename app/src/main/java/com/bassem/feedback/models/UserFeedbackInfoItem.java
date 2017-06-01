package com.bassem.feedback.models;

import com.bassem.feedback.models.datamodels.User;
import com.bassem.feedback.utils.Constants;

import java.util.Date;

/**
 * Created by Bassem Samy on 6/1/2017.
 * A model that extends User class, it contains extra properties and methods required for the business logic
 */

public class UserFeedbackInfoItem extends User implements Comparable<UserFeedbackInfoItem> {

    private String title;
    private UserFeedbackInfoItemType type = UserFeedbackInfoItemType.RECORD;
    private String lastFeedbackSent;
    private long lastFeedbackTimeDifference;

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
            lastFeedbackTimeDifference = calculateLastFeedbackTimeDifference();
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
    private long calculateLastFeedbackTimeDifference() {
        if (getLastInteractions() != null && getLastInteractions().size() > 0 && getLastInteractions().get(0).getDate() != null) {
            Date date = new Date();
            return date.getTime() - getLastInteractions().get(0).getDate().getTime();

        } else {
            return Long.MAX_VALUE;
        }
    }


}
