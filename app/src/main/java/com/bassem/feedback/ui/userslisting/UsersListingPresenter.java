package com.bassem.feedback.ui.userslisting;

import com.bassem.feedback.models.UserFeedbackInfoItem;

import java.util.List;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public interface UsersListingPresenter {
    void loadUsersFeedbackInfoItems(String fileName);
    void prepareUsersFeedbackInfoItems(List<UserFeedbackInfoItem> items);
    void onDestroy();
}
