package com.github.hubreader.activity;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.github.hubreader.Post;
import com.github.hubreader.PostMapper;
import com.github.hubreader.R;
import com.github.hubreader.data.PostProvider;
import com.github.hubreader.task.HtmlPageReader;

/**
 * @author Almazko
 */
public class PostActivity extends Activity {

    private Post news;
    public static final Uri URI_POSTS = Uri.parse("content://posts");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        Bundle extras = getIntent().getExtras();
        int postId = extras.getInt(Post.ID);

        Uri uri = Uri.parse(PostProvider.URI_POSTS.toString() + "/" + postId);


        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.getCount() < 1) {
            return;
        }

        cursor.moveToFirst();
        Post post = PostMapper.toPost(cursor);

        HtmlPageReader task = new HtmlPageReader(this, post.getLink());
        task.execute();
    }

    public void showPage(String result) {
//        TextView content = (TextView) findViewById(R.id.content);
//        content.setText(Html.fromHtml(result));


    }
}