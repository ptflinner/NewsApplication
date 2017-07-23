package com.example.patrick.newsapplication.database_classes;

import android.provider.BaseColumns;

/**
 * Created by Patrick on 7/22/2017.
 */

//Allows for easy database name calls. Harder to make errors if names can be called
public class Contract {
    public static class TABLE_ARTICLES implements BaseColumns{
        public static final String TABLE_NAME="articles";
        public static final String COLUMN_NAME_AUTHOR="author";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_DESCRIPTION="description";
        public static final String COLUMN_NAME_WEB_URL="web_url";
        public static final String COLUMN_NAME_IMG_URL="img_url";
        public static final String COLUMN_NAME_PUBLISHED="published";
    }
}
