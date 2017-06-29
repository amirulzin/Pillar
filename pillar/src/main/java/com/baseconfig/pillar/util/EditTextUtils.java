package com.baseconfig.pillar.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;

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


    public static abstract class ReactiveTextWatcher<T> implements TextWatcher {

        private WeakReference<T> tRef = new WeakReference<>(null);

        public void setObject(T object) {
            this.tRef = new WeakReference<>(object);
        }

        @Override
        public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
        }

        @Override
        public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        }

        @Override
        public void afterTextChanged(final Editable s) {
            T t = tRef.get();
            if (t != null) {
                afterChanged(t, s);
            }
        }

        protected abstract void afterChanged(final T t, final Editable s);
    }

}
