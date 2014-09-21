package com.cocoabu.omniauthtimelineforandroid.test.ui.activity;

import android.test.ActivityInstrumentationTestCase2;

import com.cocoabu.omniauthtimelineforandroid.ui.activity.LoginActivity;

/**
 * Created by kato on 2014/09/21.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    public void testTrueはTrue() {
        assertEquals(true, true);
    }
}
