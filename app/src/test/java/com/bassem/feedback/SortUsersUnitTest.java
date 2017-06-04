package com.bassem.feedback;

import android.content.res.Resources;

import com.bassem.feedback.models.UserFeedbackInfoItem;
import com.bassem.feedback.models.datamodels.UsersResponse;
import com.bassem.feedback.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/** Unit test for the sorting of users according to their last interaction in descending order
 * Created by Bassem Samy on 6/5/2017.
 */

public class SortUsersUnitTest {
    List<UserFeedbackInfoItem> users;

    @Before
    public void setup() throws IOException {
        InputStream stream = ClassLoader.getSystemResourceAsStream("users.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();

        Gson gson = new GsonBuilder().setDateFormat(Constants.INTERACTIONS_DATE_FORMAT).create();
        UsersResponse response = gson.fromJson(builder.toString(), UsersResponse.class);
        users = response.getUsers();

    }

    @Test
    public void testSorting() {
        Collections.sort(users);
        assertEquals("Cullens Doe", users.get(0).getName()); // test oldest user
        assertEquals("User2",users.get(1).getName()); // test second user
        assertEquals("Emma Cullen",users.get(users.size()-1).getName()); // test last user // more recent one
    }
}
