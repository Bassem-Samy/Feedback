package com.bassem.feedback.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.bassem.feedback.models.datamodels.LastInteraction;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;
import org.joda.time.Weeks;

/**
 * Created by Bassem Samy on 6/4/2017.
 */

public class LastInteractionInfoItem extends LastInteraction implements Parcelable {
    private int hoursDifference = -1;
    private int monthsDifference = -1;
    private int daysDifference = -1;
    private int weekDifference = -1;
    private LastInteractionSeverityType severityType = LastInteractionSeverityType.SEVER;

    public boolean isPrepared() {
        return isPrepared;
    }

    public void setPrepared(boolean prepared) {
        isPrepared = prepared;
    }

    private boolean isPrepared = false;

    public LastInteractionInfoItem() {
        super();
    }

    protected LastInteractionInfoItem(Parcel in) {
        super(in);
        hoursDifference = in.readInt();
        monthsDifference = in.readInt();
        daysDifference = in.readInt();
        weekDifference = in.readInt();
        isPrepared = in.readByte() != 0;
        severityType = (LastInteractionSeverityType) in.readSerializable();
    }

    public static final Creator<LastInteractionInfoItem> CREATOR = new Creator<LastInteractionInfoItem>() {
        @Override
        public LastInteractionInfoItem createFromParcel(Parcel in) {
            return new LastInteractionInfoItem(in);
        }

        @Override
        public LastInteractionInfoItem[] newArray(int size) {
            return new LastInteractionInfoItem[size];
        }
    };

    public void prepareTimeDifference(DateTime timeToCompareTo) {
        this.isPrepared = true;
        DateTime lastInteractionTime = new DateTime(getDate().getTime());
        setHoursDifference(Hours.hoursBetween(lastInteractionTime, timeToCompareTo).getHours());
        setDaysDifference(Days.daysBetween(lastInteractionTime, timeToCompareTo).getDays());
        setMonthsDifference(Months.monthsBetween(lastInteractionTime, timeToCompareTo).getMonths());
        setWeekDifference(Weeks.weeksBetween(lastInteractionTime, timeToCompareTo).getWeeks());
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

    public int getWeekDifference() {
        return weekDifference;
    }

    public void setWeekDifference(int weekDifference) {
        this.weekDifference = weekDifference;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(hoursDifference);
        parcel.writeInt(monthsDifference);
        parcel.writeInt(daysDifference);
        parcel.writeInt(weekDifference);
        parcel.writeByte((byte) (isPrepared ? 1 : 0));
        parcel.writeSerializable(severityType);
    }

    public LastInteractionSeverityType getSeverityType() {
        return severityType;
    }

    public void setSeverityType(LastInteractionSeverityType severityType) {
        this.severityType = severityType;
    }
}
