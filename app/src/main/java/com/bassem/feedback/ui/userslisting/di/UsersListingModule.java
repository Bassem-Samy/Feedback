package com.bassem.feedback.ui.userslisting.di;

import android.content.Context;
import android.content.res.AssetManager;

import com.bassem.feedback.ui.userslisting.UsersListingInteractor;
import com.bassem.feedback.ui.userslisting.UsersListingInteractorImpl;
import com.bassem.feedback.ui.userslisting.UsersListingPresenterImpl;
import com.bassem.feedback.ui.userslisting.UsersListingView;
import com.bassem.feedback.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bassem Samy on 6/2/2017.
 */
@Module
public class UsersListingModule {
    private Context mContext;
    private UsersListingView mView;

    // the view and the context to get the asset manager from
    public UsersListingModule(UsersListingView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    // Provides the view required for presenter creation
    @Singleton
    @Provides
    public UsersListingView providesUsersListingView() {
        return this.mView;
    }

    // provides asset manager required for the interactor creation
    @Singleton
    @Provides
    public AssetManager providesAssetManager() {
        return this.mContext.getAssets();
    }

    // provides gson required for the interactor creation
    @Singleton
    @Provides
    public Gson providesGson() {
        return new GsonBuilder().setDateFormat(Constants.INTERACTIONS_DATE_FORMAT).create();
    }

    // provides the interactor
    @Singleton
    @Provides
    public UsersListingInteractor providesUsersListingInteractor(AssetManager assetManager, Gson gson) {
        return new UsersListingInteractorImpl(assetManager, gson);
    }

    // provides the presenter
    @Singleton
    @Provides
    public UsersListingPresenterImpl providesUsersListingPresenterImpl(UsersListingView view, UsersListingInteractor interactor) {
        return new UsersListingPresenterImpl(view, interactor);
    }
}
