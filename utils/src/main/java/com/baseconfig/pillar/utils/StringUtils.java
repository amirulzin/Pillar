package com.baseconfig.pillar.utils;

import android.support.annotation.NonNull;

import java.net.URL;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern pUpper = Pattern.compile("(?=\\p{Upper})");

    public static String toJsonStylePropertyName(@NonNull final String str) {
        return parsePropertyName(str);
    }

    private static String parsePropertyName(final String str) {
        final int len = str.length();
        if (len <= 2) return str;
        final String[] split = pUpper.split(str);
        final StringBuilder b = new StringBuilder(str.length() * 2);
        final String und = "_";
        for (int i = 0; i < split.length; i++) {
            if (i != split.length - 1) {
                b.append(split[i]).append(und);
            } else b.append(split[i]);
        }
        return b.toString().toLowerCase();
    }


    /**
     * This function will take an URL as input and return the file name. <p>Examples : <ul>
     * <li>http://example.com/a/b/c/test.txt = test.txt <li>http://example.com/ = an empty string
     * <li>http://example.com/test.txt?param=value = test.txt <li>http://example.com/test.txt#anchor
     * = test.txt </ul>
     *
     * @param url The input URL
     * @return The URL file name
     * See <a href="http://stackoverflow.com/a/26810644">StackOverflow Answer</a>
     */
    public static String getFileNameFromUrl(URL url) {
        String urlString = url.getFile();
        return urlString.substring(urlString.lastIndexOf('/') + 1).split("\\?")[0].split("#")[0];
    }
}
