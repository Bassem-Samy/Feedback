package com.bassem.feedback.ui.userslisting;

import com.bassem.feedback.models.UserFeedbackInfoItem;

import java.util.List;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public interface UsersListingView {
    void updateData(List<UserFeedbackInfoItem> items);
    void showError();
    void showProgress();
    void hideProgress();
}
