package com.github.hubreader.data;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

/**
 * @author Almazko
 */
public class PostLoader extends CursorLoader {
    public PostLoader(Context context, Uri contentUri) {
        super(context);
        setUri(contentUri);
//        RssReader task = new RssReader(null);
//        task.execute();
    }
}
