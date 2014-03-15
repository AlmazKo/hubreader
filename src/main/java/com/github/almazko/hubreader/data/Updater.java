package com.github.almazko.hubreader.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import com.github.almazko.hubreader.Post;

/**
 * @author Almazko
 */
public class Updater {

    private ContentResolver contentResolver;

    public Updater(ContentResolver contentResolver) {

        this.contentResolver = contentResolver;
    }

    public void image(Post post, byte[] bArray) {

        ContentValues values = new ContentValues();
        values.put(PostTable.IMG_PREVIEW, bArray);
        String[] p = {String.valueOf(post.id)};
        contentResolver.update(PostProvider.URI_POSTS, values, PostTable._ID + "= ?", p);
    }
}
