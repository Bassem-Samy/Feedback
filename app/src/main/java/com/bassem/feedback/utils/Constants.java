package com.bassem.feedback.utils;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public class Constants {
    // 2 weeks = 2 weeks * 7 days *24 hours * 60 minutes *60 seconds *1000 to get milli seconds
    public static final long LAST_INTERACTION_THRESHOLD = 2 * 7 * 24 * 60 * 60 * 1000;
    public static String USERS_JSON_FILE_NAME = "users.json";
    public static final String INTERACTIONS_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
}