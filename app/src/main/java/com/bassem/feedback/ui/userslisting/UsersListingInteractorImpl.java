package com.bassem.feedback.ui.userslisting;

import android.content.res.AssetManager;

import com.bassem.feedback.models.User;
import com.bassem.feedback.models.UsersResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by Bassem Samy on 6/1/2017.
 */

public class UsersListingInteractorImpl implements UsersListingInteractor {
    AssetManager mAssetManager;
    Gson mGson;

    public UsersListingInteractorImpl(AssetManager assetManager, Gson gson) {
        this.mAssetManager = assetManager;
        this.mGson = gson;
    }

    @Override
    public Single<List<User>> getUsersFromAssetsFile(final String fileName) {
        // open the file using asset maanger and return a buffered reader then map it to return a list of users
        return Single.fromCallable(new Callable<BufferedReader>() {
            @Override
            public BufferedReader call() throws Exception {
                InputStream stream = mAssetManager.open(fileName);
                return new BufferedReader(new InputStreamReader(stream));
            }
        }).map(new Function<BufferedReader, List<User>>() {
            @Override
            public List<User> apply(BufferedReader bufferedReader) throws Exception {
                UsersResponse res = mGson.fromJson(bufferedReader, UsersResponse.class);
                if (res != null) {
                    return res.getUsers();
                }
                return null;

            }
        });
    }
}
