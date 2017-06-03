package com.bassem.feedback.models;

import com.bassem.feedback.models.datamodels.LastInteraction;
import com.bassem.feedback.models.datamodels.User;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;
import org.joda.time.Weeks;

import java.util.ArrayList;
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
    private int hoursDifference = -1;
    private int monthsDifference = -1;
    private int daysDifference = -1;
    private int weekDifference = -1;

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

    public int getHoursDifference() {
        return hoursDifference;
    }

    public void setHoursDifference(int hoursDifference) {
        this.hoursDifference = hoursDifference;
    }

    public int getMonthsDifference() {
        return monthsDifference;
    }

    public void setMonthsDifference(int monthsDifference) {
        this.monthsDifference = monthsDifference;
    }

    public int getDaysDifference() {
        return daysDifference;
    }

    public void setDaysDifference(int daysDifference) {
        this.daysDifference = daysDifference;
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
            DateTime lastInteraction = new DateTime(getLastInteractions().get(0).getDate().getTime());
            setHoursDifference(Hours.hoursBetween(lastInteraction, now).getHours());
            setDaysDifference(Days.daysBetween(lastInteraction, now).getDays());
            setMonthsDifference(Months.monthsBetween(lastInteraction, now).getMonths());
            setWeekDifference(Weeks.weeksBetween(lastInteraction, now).getWeeks());
            setLastFeedbackTimeDifference(now.getMillis() - lastInteraction.getMillis());

        } else {
            setLastFeedbackTimeDifference(Long.MAX_VALUE);
        }
    }


    public int getWeekDifference() {
        return weekDifference;
    }

    public void setWeekDifference(int weekDifference) {
        this.weekDifference = weekDifference;
    }

    public void updateInteraction(Date date) {
        if (this.getLastInteractions() == null) {
            ArrayList<LastInteraction> interactions = new ArrayList<>();
        }
        LastInteraction interaction = new LastInteraction();
        interaction.setId(this.getLastInteractions().size() + 1);
        interaction.setDate(date);
        this.getLastInteractions().add(0, interaction);
        calculateLastFeedbackTimeDifference();

    }
}
