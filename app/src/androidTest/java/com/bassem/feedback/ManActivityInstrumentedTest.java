package com.bassem.feedback;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bassem.feedback.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Bassem Samy on 6/4/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ManActivityInstrumentedTest {
    private static final int ITEMS_COUNT = 10;
    private static final String LAST_ITEM_USER_EXPECTED_NAME = "Emma Cullen";
    private static final int JOHN_DOE_POSITION = 1;
    private static final String JOHN_DOE_EXPECTED_NAME = "John Doe";
    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testUserFeedbackItems() {
        // check items count
        onView(withId(R.id.rclr_users)).check(new RecyclerViewItemsCountAssertion(ITEMS_COUNT));
        // scroll to last item
        onView(withId(R.id.rclr_users)).perform(scrollToPosition(ITEMS_COUNT - 1));
        // check if last item displays the right user
        onView(withId(R.id.rclr_users)).check(new RecyclerViewItemStringDataAssertion(
                R.id.txt_user_name,
                LAST_ITEM_USER_EXPECTED_NAME,
                ITEMS_COUNT - 1)
        );
        // scroll to second item
        onView(withId(R.id.rclr_users)).perform(scrollToPosition(JOHN_DOE_POSITION));
        // perform click on add to favorite
        onView(withId(R.id.rclr_users)).perform(
                RecyclerViewActions.actionOnItemAtPosition(JOHN_DOE_POSITION, PerformClickOnChildAction.clickChildViewWithId(R.id.lnr_give_feedback)));
        // scroll to last item and check it's john doe
        onView(withId(R.id.rclr_users)).perform(scrollToPosition(ITEMS_COUNT - 1));
        // check if last item displays the right user
        onView(withId(R.id.rclr_users)).check(new RecyclerViewItemStringDataAssertion(
                R.id.txt_user_name,
                JOHN_DOE_EXPECTED_NAME,
                ITEMS_COUNT - 1)
        );
    }
}