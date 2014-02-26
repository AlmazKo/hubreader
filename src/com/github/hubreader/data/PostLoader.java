package com.github.hubreader.data;

import android.content.Context;
import android.content.CursorLoader;
import com.github.hubreader.task.RssReader;

/**
 * @author Almazko
 */
public class PostLoader extends CursorLoader {
    public PostLoader(Context context) {
        super(context);
        setUri(PostProvider.CONTENT_URI);
        RssReader task = new RssReader(null);
        task.execute();
    }
}
