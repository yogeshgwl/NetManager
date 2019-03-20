package com.internet.gwl.internal;

import android.support.design.widget.Snackbar;
import android.view.Gravity;

import com.internet.gwl.R;

public class ViewUtility {
    private static int color = R.color.color_green;
    private static int duration = Snackbar.LENGTH_LONG;
    private static int barGravity = Gravity.BOTTOM;
    private static String message;

    private ViewUtility() {
    }

    public static int getBarGravity() {
        return barGravity;
    }

    public static void setBarGravity(int barGravity) {
        ViewUtility.barGravity = barGravity;
    }

    public static int getColor() {
        return color;
    }

    public static void setColor(int color) {
        ViewUtility.color = color;
    }

    public static int getDuration() {
        return duration;
    }

    public static void setDuration(int duration) {
        ViewUtility.duration = duration;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        ViewUtility.message = message;
    }
}
