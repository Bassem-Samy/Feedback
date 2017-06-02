package com.bassem.feedback.ui.userslisting.di;

import com.bassem.feedback.ui.userslisting.UsersListingFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bassem Samy on 6/2/2017.
 */
@Singleton
@Component(modules = {UsersListingModule.class})
public interface UsersListingComponent {
    void inject(UsersListingFragment target);
}
