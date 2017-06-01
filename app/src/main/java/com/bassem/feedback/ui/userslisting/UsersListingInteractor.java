package com.bassem.feedback.ui.userslisting;

import android.content.res.AssetManager;

import com.bassem.feedback.models.User;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public interface UsersListingInteractor {
    Single<List<User>> getUsersFromAssetsFile(String fileName);
}
