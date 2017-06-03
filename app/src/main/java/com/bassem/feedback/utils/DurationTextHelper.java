package com.bassem.feedback.utils;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.bassem.feedback.R;
import com.bassem.feedback.models.UserFeedbackInfoItem;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.RecursiveTask;

/**
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

    public String getDurationTextResourceId(UserFeedbackInfoItem item) {
        if (item.getHoursDifference() == -1 && item.getDaysDifference() == -1 && item.getMonthsDifference() == -1) {
            return neverText;
        }
        if (item.getMonthsDifference() >= 1) {
            return String.format(StRING_FORMAT, item.getMonthsDifference(), item.getMonthsDifference() == 1 ? monthAgoText : monthsAgoText);
        } else if (item.getWeekDifference() >= 2) {
            return String.format(StRING_FORMAT, item.getWeekDifference(), item.getWeekDifference() == 1 ? weekAgoText : weeksAgoText);
        } else if (item.getDaysDifference() >= 1) {
            return String.format(StRING_FORMAT, item.getDaysDifference(), item.getDaysDifference() == 1 ? dayAgoText : daysAgoText);
        } else if (item.getHoursDifference() >= 1) {
            return String.format(StRING_FORMAT, item.getHoursDifference(), item.getHoursDifference() == 1 ? hourAgoText : hoursAgoText);
        } else if (item.getHoursDifference() == 0) {
            return justNowText;
        }
        return neverText;

    }

}
