package com.example.android.moviebrowser;

import android.text.TextUtils;

public class QueryUtils {
    private static final String SEPARATOR = "+";

    public static String convertNormalQueryToParamQuery(String query) {
        if (TextUtils.isEmpty(query)) return null;
        return query.replaceAll("\\s+", SEPARATOR);
    }
}
