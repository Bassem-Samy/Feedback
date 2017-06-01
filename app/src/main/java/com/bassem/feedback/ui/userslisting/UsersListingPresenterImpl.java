package com.bassem.feedback.ui.userslisting;

import com.bassem.feedback.models.UserFeedbackInfoItem;
import com.bassem.feedback.models.UserFeedbackInfoItemType;
import com.bassem.feedback.utils.Constants;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public class UsersListingPresenterImpl implements UsersListingPresenter {
    UsersListingView mView;
    UsersListingInteractor mInteractor;
    Disposable mDisposable;

    public UsersListingPresenterImpl(UsersListingView view, UsersListingInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    /**
     * Loads users from json file in assets folder
     *
     * @param fileName to load json from
     */
    @Override
    public void loadUsersFeedbackInfoItems(String fileName) {
        disposeLoadUsersSubscription();
        mDisposable = mInteractor.getUsersFeedbackInfoItemsFromAssetsFile(fileName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        new Consumer<List<UserFeedbackInfoItem>>() {
                            @Override
                            public void accept(List<UserFeedbackInfoItem> items) throws Exception {
                                mView.hideProgress();
                                if (items != null && items.size() > 0) {
                                    prepareUsersFeedbackInfoItems(items);
                                    mView.updateData(items);
                                } else {
                                    mView.showError();
                                }
                            }
                        }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showError();
                            }
                        });
    }

    /**
     * Sorts  the items by last interaction time difference
     * Then add section items in correct indices
     * @param items
     */
    @Override
    public void prepareUsersFeedbackInfoItems(List<UserFeedbackInfoItem> items) {
        Collections.sort(items);
        UserFeedbackInfoItem giveFeedbackSectionItem = new UserFeedbackInfoItem();
        giveFeedbackSectionItem.setType(UserFeedbackInfoItemType.SECTION);
        items.add(0, giveFeedbackSectionItem);
        int recentlyGivenFeedbackIndex = -1;
        for (int i = 1; i < items.size(); i++) {
            if (items.get(i).getLastFeedbackTimeDifference() < Constants.LAST_INTERACTION_THRESHOLD) {
                recentlyGivenFeedbackIndex = i;
                break;
            }
        }
        UserFeedbackInfoItem recentlyGivenFeedbackItem = new UserFeedbackInfoItem();
        recentlyGivenFeedbackItem.setType(UserFeedbackInfoItemType.SECTION);
        if (recentlyGivenFeedbackIndex > -1) {
            items.add(recentlyGivenFeedbackIndex, recentlyGivenFeedbackItem);
        } else {
            items.add(recentlyGivenFeedbackItem);
        }

    }

    @Override
    public void onDestroy() {
        disposeLoadUsersSubscription();
    }

    /**
     * Disposes the load users subscription
     */
    private void disposeLoadUsersSubscription() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
