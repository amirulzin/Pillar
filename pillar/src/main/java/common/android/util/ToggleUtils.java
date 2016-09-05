package common.android.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ToggleUtils {

    public static List<RadioButton> getRadioButtons(RadioGroup radioGroup) {
        final ArrayList<RadioButton> out = new ArrayList<>(radioGroup.getChildCount());
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            final View v = radioGroup.getChildAt(i);
            if (v instanceof RadioButton) {
                out.add((RadioButton) v);
            }
        }
        return out;
    }

    public static void setupRadioButtons(Context context, final int checkedColor, final int normalColor, final RadioGroup radioGroup) {
        final ArrayMap<Integer, RadioButton> buttons = getRadioButtonsMap(radioGroup);
        for (Map.Entry<Integer, RadioButton> button : buttons.entrySet()) {
            button.getValue()
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        final List<Drawable> wrappedDrawables = new ArrayList<>(4);
                        boolean init = false;

                        @Override
                        public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                            if (!init) {
                                init = true;
                                for (final Drawable d : buttonView.getCompoundDrawables()) {
                                    if (d != null) {
                                        wrappedDrawables.add(DrawableCompat.wrap(d));
                                    }
                                }
                            }
                            for (final Drawable d : wrappedDrawables) {
                                if (isChecked) {
                                    DrawableCompat.setTint(d, checkedColor);
                                } else DrawableCompat.setTint(d, normalColor);
                            }
                        }
                    });
        }
    }

    public static ArrayMap<Integer, RadioButton> getRadioButtonsMap(RadioGroup radioGroup) {
        final ArrayMap<Integer, RadioButton> out = new ArrayMap<>(radioGroup.getChildCount());
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            final View v = radioGroup.getChildAt(i);
            if (v instanceof RadioButton) {
                out.put(v.getId(), (RadioButton) v);
            }
        }
        return out;
    }
}
