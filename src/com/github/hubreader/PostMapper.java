package com.github.hubreader;

import android.content.ContentValues;
import android.database.Cursor;

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
}
