package com.bassem.feedback.models;

/**
 * Created by Bassem Samy on 6/1/2017.
 * Type enum for FeedbackInfoItem to determine it's type, whether it's a section title ( last interacted with or not ) or it's a user info item with interaction option.
 */

public enum UserFeedbackInfoItemType {
    SECTION(1), RECORD(2);
    private final int value;

    UserFeedbackInfoItemType(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
