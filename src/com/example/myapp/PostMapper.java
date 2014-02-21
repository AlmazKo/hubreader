package com.example.myapp;

import android.content.ContentValues;

import static com.example.myapp.data.Post.*;

/**
 * @author Almazko
 */
public class PostMapper {

    public static ContentValues toValues(Post post) {

        ContentValues values = new ContentValues();

        values.put(LINK, post.link.toString());
        values.put(TITLE, post.description);

        return values;
    }
}
