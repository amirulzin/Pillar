package com.baseconfig.pillar.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.EditText;

public class EditTextUtils {

    public static void lockEditText(final boolean locked, final EditText editText) {
        editText.clearFocus();
        if (locked) {
            editText.setFocusable(false);
            editText.setCursorVisible(false);
        } else {
            editText.setCursorVisible(true);
            editText.setFocusableInTouchMode(true);
        }

        Drawable background = editText.getBackground();
        if (background != null) {
            if (locked) {
                background.setAlpha(Color.TRANSPARENT);
            } else {
                background.setAlpha(255);
            }
        }
    }
}
