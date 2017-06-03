package com.bassem.feedback.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.bassem.feedback.R;
import com.bassem.feedback.ui.userslisting.UsersListingFragment;

public class MainActivity extends AppCompatActivity implements UsersListingFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void onUserClicked() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
