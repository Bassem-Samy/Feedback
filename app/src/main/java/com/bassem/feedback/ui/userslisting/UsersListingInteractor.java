package com.bassem.feedback.ui.userslisting;

import com.bassem.feedback.models.UserFeedbackInfoItem;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public interface UsersListingInteractor {
    Single<List<UserFeedbackInfoItem>> getUsersFeedbackInfoItemsFromAssetsFile(String fileName);
}
