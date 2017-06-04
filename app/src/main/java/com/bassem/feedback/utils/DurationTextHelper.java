package com.bassem.feedback.utils;

import android.content.Context;

import com.bassem.feedback.R;
import com.bassem.feedback.models.LastInteractionInfoItem;
import com.bassem.feedback.models.LastInteractionSeverityType;

import org.joda.time.DateTime;

import java.lang.ref.WeakReference;

/**
 * Helper class that calculates readable text for last interactions
 * Created by Bassem Samy on 6/3/2017.
 */

public class DurationTextHelper {

    private final static long HOUR_DIFFERENCE = 60L * 60L * 1000L;
    private final static long TWO_HOURS_DIFFERENCE = 2L * 60L * 60L * 1000L;
    private final static long DAY_DIFFERENCE = 24L * 60L * 60L * 1000L;
    private final static long SEVEN_DAYS_DIFFERENCE = 7L * 24L * 60L * 60L * 1000L;
    private final static long FOURTEEN_DAYS_DIFFERENCE = 14L * 24L * 60L * 60L * 1000L;
    private final static long MONTH_DIFFERENCE = 30L * 24L * 60L * 60L * 1000L;

    private final static String StRING_FORMAT = "%d %s";
    private String justNowText;
    private String hourAgoText;
    private String hoursAgoText;
    private String dayAgoText;
    private String daysAgoText;
    private String monthAgoText;
    private String monthsAgoText;
    private String neverText;
    private String weekAgoText;
    private String weeksAgoText;

    public DurationTextHelper(Context context) {
        initializeTextStrings(new WeakReference<Context>(context).get());
    }

    private void initializeTextStrings(Context context) {
        justNowText = context.getString(R.string.just_now);
        hourAgoText = context.getString(R.string.hour_ago);
        hoursAgoText = context.getString(R.string.hours_ago);
        dayAgoText = context.getString(R.string.day_ago);
        daysAgoText = context.getString(R.string.days_ago);
        weekAgoText = context.getString(R.string.week_ago);
        weeksAgoText = context.getString(R.string.weeks_ago);
        monthAgoText = context.getString(R.string.month_ago);
        monthsAgoText = context.getString(R.string.months_ago);
        neverText = context.getString(R.string.never);
    }

    /**
     * Checks last interaction timings and returns the relevant text for it
     *
     * @param lastInteraction item
     * @return readable text for last interaction time
     */
    public String getLastInteractionDuration(LastInteractionInfoItem lastInteraction) {
        if (lastInteraction == null) {
            return neverText;
        }

        if (lastInteraction.getHoursDifference() == -1 && lastInteraction.getWeekDifference() == -1 && lastInteraction.getDaysDifference() == -1 && lastInteraction.getMonthsDifference() == -1) {
            return neverText;
        }

        if (lastInteraction.getMonthsDifference() >= 1) {
            lastInteraction.setSeverityType(LastInteractionSeverityType.SEVER);
            return String.format(StRING_FORMAT, lastInteraction.getMonthsDifference(), lastInteraction.getMonthsDifference() == 1 ? monthAgoText : monthsAgoText);
        } else if (lastInteraction.getWeekDifference() >= 2) {
            lastInteraction.setSeverityType(LastInteractionSeverityType.MEDIUM);
            return String.format(StRING_FORMAT, lastInteraction.getWeekDifference(), lastInteraction.getWeekDifference() == 1 ? weekAgoText : weeksAgoText);
        } else if (lastInteraction.getDaysDifference() >= 1) {
            lastInteraction.setSeverityType(LastInteractionSeverityType.NORMAL);
            return String.format(StRING_FORMAT, lastInteraction.getDaysDifference(), lastInteraction.getDaysDifference() == 1 ? dayAgoText : daysAgoText);
        } else if (lastInteraction.getHoursDifference() >= 1) {
            lastInteraction.setSeverityType(LastInteractionSeverityType.NORMAL);
            return String.format(StRING_FORMAT, lastInteraction.getHoursDifference(), lastInteraction.getHoursDifference() == 1 ? hourAgoText : hoursAgoText);
        } else if (lastInteraction.getHoursDifference() == 0) {
            lastInteraction.setSeverityType(LastInteractionSeverityType.NORMAL);
            return justNowText;
        }
        return neverText;
    }

}
