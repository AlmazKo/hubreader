package com.github.hubreader;

import android.content.ContentValues;
import android.database.Cursor;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.github.hubreader.data.PostTable.*;

/**
 * @author Almazko
 */
public class PostMapper {

    private static final SimpleDateFormat FORMATTER;

    static {
        FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FORMATTER.setDateFormatSymbols(DateFormatSymbols.getInstance(Locale.ENGLISH));
    }


    public static ContentValues toValues(Post post) {
        ContentValues values = new ContentValues();

        values.put(ID, post.id);
        values.put(TITLE, post.title);
        values.put(DESCRIPTION, post.description);
        values.put(DATE_PUBLISH, getDateTime(post.publishDate));
        if (post.previewLink != null) {
            values.put(LINK_PREVIEW, post.previewLink.toString());
        }

        return values;
    }

    public static Post toPost(Cursor cursor) {
        if (cursor.getCount() < 1) {
            return null;
        }

        Post post = new Post();
        post.title = cursor.getString(cursor.getColumnIndex(TITLE));
        post.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
        post.publishDate = getDateTime(cursor.getString(cursor.getColumnIndex(DATE_PUBLISH)));
        post.id = cursor.getInt(cursor.getColumnIndex(ID));

        String previewLink = cursor.getString(cursor.getColumnIndex(LINK_PREVIEW));

        if (previewLink != null && previewLink.length() > 0) {
            try {
                post.previewLink = new URL(previewLink);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        return post;
    }

    public static String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        return dateFormat.format(date);
    }

    public static Date getDateTime(String date) {

        try {
            return FORMATTER.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
