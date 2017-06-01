package com.bassem.feedback.ui.userslisting;

import com.bassem.feedback.models.User;

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
     * @param fileName to load json from
     */
    @Override
    public void loadUsers(String fileName) {
        disposeLoadUsersSubscription();
        mDisposable = mInteractor.getUsersFromAssetsFile(fileName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        new Consumer<List<User>>() {
                            @Override
                            public void accept(List<User> users) throws Exception {
                                mView.hideProgress();
                                if (users != null && users.size() > 0) {
                                    mView.updateData(users);
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
