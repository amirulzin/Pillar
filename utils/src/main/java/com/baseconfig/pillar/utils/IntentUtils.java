package com.baseconfig.pillar.utils;

import android.content.Intent;
import android.support.annotation.Nullable;

public class IntentUtils {

    public static Intent requestPicture(@Nullable final Intent optionalIntent, @Nullable final String optionalChooserTitle) {
        Intent out = optionalIntent == null ? new Intent() : optionalIntent;
        out.setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
        return Intent.createChooser(out, optionalChooserTitle);
    }
}
