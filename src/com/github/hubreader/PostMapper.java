package com.github.hubreader;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.github.hubreader.data.PostTable.*;

/**
 * @author Almazko
 */
public class PostMapper {

    public static ContentValues toValues(Post post) {

        ContentValues values = new ContentValues();

        values.put(LINK, post.link.toString());
        values.put(TITLE, post.title);
        values.put(DESCRIPTION, post.description);
        values.put(DATE_CREATE, getDateTime(post.date));

        return values;
    }

    public static Post toPost(Cursor cursor) {
        Post post = new Post();
        post.title = cursor.getString(cursor.getColumnIndex(TITLE));
        post.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
        post.setLink(cursor.getString(cursor.getColumnIndex(LINK)));
        post.setPreviewLink(cursor.getString(cursor.getColumnIndex(LINK_PREVIEW)));

        return post;
    }

    private static String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        return dateFormat.format(date);
    }
}
