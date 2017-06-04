package com.bassem.feedback.ui;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bassem.feedback.R;
import com.bassem.feedback.models.UserFeedbackInfoItem;
import com.bassem.feedback.ui.userdetails.UserDetailsFragment;
import com.bassem.feedback.ui.userslisting.UsersListingFragment;
import com.bassem.feedback.utils.DetailsTransition;

public class MainActivity extends AppCompatActivity implements UsersListingFragment.OnFragmentInteractionListener {
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);
        addUsersListingFragment();
    }

    private void addUsersListingFragment() {
        if (getSupportFragmentManager().findFragmentByTag(UsersListingFragment.TAG) == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frm_container, UsersListingFragment.newInstance(), UsersListingFragment.TAG)
                    .commit();

        }
    }

    @Override
    public void onUserClicked(UserFeedbackInfoItem item) {
        openUserDetailsFragment(item);
    }

    @Override
    public void onFeedbackGiven(UserFeedbackInfoItem item) {
        UserDetailsFragment userDetailsFragment = (UserDetailsFragment) getSupportFragmentManager().findFragmentByTag(UserDetailsFragment.TAG);
        if (userDetailsFragment != null) {
            userDetailsFragment.updateUserToDisplay(item);
        }
    }

    private void openUserDetailsFragment(UserFeedbackInfoItem item) {
        UserDetailsFragment userDetailsFragment = (UserDetailsFragment) getSupportFragmentManager().findFragmentByTag(UserDetailsFragment.TAG);

        if (userDetailsFragment == null) {
            userDetailsFragment = UserDetailsFragment.newInstance(item);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frm_details_container, userDetailsFragment,
                            UserDetailsFragment.TAG)
                    .addToBackStack(null)
                    .commit();
            showOrHideResetMenu(false);
        } else {
            userDetailsFragment.updateUserToDisplay(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        return true;
    }

    void showOrHideResetMenu(boolean show) {
        if (this.menu != null) {
            MenuItem item = this.menu.findItem(R.id.reset_menu_item);
            item.setVisible(show);
        }
    }

    FragmentManager.OnBackStackChangedListener mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                showOrHideResetMenu(true);
            }
        }
    };
}
