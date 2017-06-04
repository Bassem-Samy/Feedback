package com.bassem.feedback.ui.userslisting;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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
    private static final String SAVED_USERS_LIST = "saved_users_list";
    private static final String SAVED_LAYOUT_MANAGER_STATE = "saved_layout_manager_state";
    private OnFragmentInteractionListener mListener;
    @Inject
    UsersListingPresenterImpl presenter;
    @BindView(R.id.rclr_users)
    RecyclerView usersRecyclerView;
    @BindView(R.id.prgrs_main)
    ProgressBar mainProgressBar;
    UsersListingAdapter usersListingAdapter;
    Parcelable layoutManagerSavedState;
    LinearLayoutManager mLinearLayoutManager;

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
        List<UserFeedbackInfoItem> savedItems = null;
        // check for saved instance data
        if (savedInstanceState != null) {
            savedItems = savedInstanceState.getParcelableArrayList(SAVED_USERS_LIST);
            layoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER_STATE);
        }
        if (savedItems != null && savedItems.size() > 0) {
            updateData(savedItems);


        } else {
            presenter.loadUsersFeedbackInfoItems(Constants.USERS_JSON_FILE_NAME);
        }


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
        if (usersListingAdapter == null) {
            usersListingAdapter = new UsersListingAdapter(items, onFeedbackInfoItemClick, getContext());
            usersRecyclerView.setAdapter(usersListingAdapter);
        } else {
            usersListingAdapter.setItems(items);
        }
        if (mLinearLayoutManager == null) {
            mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            usersRecyclerView.setLayoutManager(mLinearLayoutManager);
        }
        if (layoutManagerSavedState != null) {
            usersRecyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
        }
        usersRecyclerView.setItemAnimator(getRecyclerViewAnimator());
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
        if(presenter!=null){
            presenter.onDestroy();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // on user clicked
        void onUserClicked(UserFeedbackInfoItem item);
        void onFeedbackGiven(UserFeedbackInfoItem item);
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
            if (mListener != null) {

                mListener.onUserClicked(usersListingAdapter.getItemByPosition(position));
            }
        }

        @Override
        public void onFeedbackGiven(UserFeedbackInfoItem item) {
            usersRecyclerView.scrollToPosition(usersListingAdapter.getItemCount() - 1);
            mListener.onFeedbackGiven(item);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        super.onSaveInstanceState(outState);
        if (usersListingAdapter != null) {
            if (usersListingAdapter.getDataset() != null && usersListingAdapter.getDataset().size() > 0) {
                outState.putParcelableArrayList(SAVED_USERS_LIST, usersListingAdapter.getDataset());
            }
        }
        if (usersRecyclerView != null && usersRecyclerView.getLayoutManager() != null) {
            outState.putParcelable(SAVED_LAYOUT_MANAGER_STATE, usersRecyclerView.getLayoutManager().onSaveInstanceState());
        }
    }
}
