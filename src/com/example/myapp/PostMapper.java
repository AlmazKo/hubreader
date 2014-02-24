package com.example.myapp;

import android.content.ContentValues;
import android.database.Cursor;

import static com.example.myapp.data.PostTable.*;

/**
 * @author Almazko
 */
public class PostMapper {

    public static ContentValues toValues(Post post) {

        ContentValues values = new ContentValues();

        values.put(LINK, post.link.toString());
        values.put(TITLE, post.title);
        values.put(DESCRIPTION, post.description);

        return values;
    }

    public static Post toPost(Cursor cursor) {
        Post post = new Post();
        post.title = cursor.getString(cursor.getColumnIndex(TITLE));
        post.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
        post.setLink(cursor.getString(cursor.getColumnIndex(LINK)));

        return post;
    }
}
