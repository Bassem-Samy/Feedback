package com.bassem.feedback.utils;

import com.bassem.feedback.R;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public class Constants {
    // 2 weeks = 2 weeks * 7 days *24 hours * 60 minutes *60 seconds *1000 to get milli seconds
    public static final long LAST_INTERACTION_THRESHOLD = 2L * 7L * 24L * 60L * 60L * 1000L;
    public static String USERS_JSON_FILE_NAME = "users.json";
    public static final String INTERACTIONS_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final int NORMAL_SEVERITY_COLOR_RESOURCE_ID = R.color.normal_severity_color;
    public static final int MEDIUM_SEVERITY_COLOR_RESOURCE_ID = R.color.medium_severity_color;
    public static final int SEVER_COLOR_RESOURCE_ID = R.color.sever_color;
}
