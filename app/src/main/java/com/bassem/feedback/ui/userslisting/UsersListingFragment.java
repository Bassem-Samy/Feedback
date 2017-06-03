package com.bassem.feedback.ui.userslisting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bassem.feedback.R;
import com.bassem.feedback.adapters.UsersListingAdapter;
import com.bassem.feedback.models.UserFeedbackInfoItem;
import com.bassem.feedback.models.datamodels.User;
import com.bassem.feedback.ui.userslisting.di.DaggerUsersListingComponent;
import com.bassem.feedback.ui.userslisting.di.UsersListingModule;
import com.bassem.feedback.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private static final long RECYCLER_VIEW_ANIMATION_DURATION = 500;
    private OnFragmentInteractionListener mListener;
    @Inject
    UsersListingPresenterImpl presenter;
    @BindView(R.id.rclr_users)
    RecyclerView usersRecyclerView;
    @BindView(R.id.prgrs_main)
    ProgressBar mainProgressBar;
    UsersListingAdapter usersListingAdapter;

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
        setHasOptionsMenu(true);
        DaggerUsersListingComponent.builder().usersListingModule(new UsersListingModule(this, getContext())).build().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_listing, container, false);
        ButterKnife.bind(this, view);
        return view;
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

        usersListingAdapter = new UsersListingAdapter(items, onFeedbackInfoItemClick, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        usersRecyclerView.setLayoutManager(manager);
        usersRecyclerView.setItemAnimator(getRecyclerViewAnimator());
        usersRecyclerView.setAdapter(usersListingAdapter);
        usersListingAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError() {

    }

    @Override
    public void showProgress() {
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mainProgressBar.setVisibility(View.GONE);
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

    RecyclerView.ItemAnimator getRecyclerViewAnimator() {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(RECYCLER_VIEW_ANIMATION_DURATION);
        itemAnimator.setRemoveDuration(RECYCLER_VIEW_ANIMATION_DURATION);
        return itemAnimator;
    }

    UsersListingAdapter.OnFeedbackInfoItemClick onFeedbackInfoItemClick = new UsersListingAdapter.OnFeedbackInfoItemClick() {
        @Override
        public void onUserClicked(int position) {

        }

        @Override
        public void onFeedbackGiven() {
            usersRecyclerView.scrollToPosition(usersListingAdapter.getItemCount() - 1);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset_menu_item: {
                if (presenter != null) {
                    presenter.loadUsersFeedbackInfoItems(Constants.USERS_JSON_FILE_NAME);
                }
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }
}
