package org.ravenlabs.fuelpass.core;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Ravenheart on 28.11.2014 г..
 */
public class UIUtils {
    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
