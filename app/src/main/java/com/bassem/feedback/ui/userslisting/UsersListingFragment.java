package com.bassem.feedback.ui.userslisting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bassem.feedback.R;
import com.bassem.feedback.models.UserFeedbackInfoItem;
import com.bassem.feedback.models.datamodels.User;
import com.bassem.feedback.ui.userslisting.di.DaggerUsersListingComponent;
import com.bassem.feedback.ui.userslisting.di.UsersListingModule;
import com.bassem.feedback.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple Fragment that displays list of users with their feedback statuses.
 * Activities that contain this fragment must implement the
 * {@link UsersListingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UsersListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersListingFragment extends Fragment implements UsersListingView {
    public static final String TAG = "users_listing_fragment";
    private OnFragmentInteractionListener mListener;
    @Inject
    UsersListingPresenterImpl presenter;

    public UsersListingFragment() {
        // Required empty public constructor
    }

    public static UsersListingFragment newInstance() {
        UsersListingFragment fragment = new UsersListingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerUsersListingComponent.builder().usersListingModule(new UsersListingModule(this, getContext())).build().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_listing, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.loadUsersFeedbackInfoItems(Constants.USERS_JSON_FILE_NAME);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateData(List<UserFeedbackInfoItem> items) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // call presenter onDestroy();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // on user clicked
        void onUserClicked();
    }
}
