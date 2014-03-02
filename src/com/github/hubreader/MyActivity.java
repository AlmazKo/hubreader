package com.github.hubreader;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.github.hubreader.data.PostLoader;
import com.github.hubreader.data.PostProvider;
import com.github.hubreader.task.RssReader;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    RssReader task;
    public static final int LOADER_MAIN = 0;
    public static final int LOADER_NEW = 1;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        touchEvents();

        getLoaderManager().initLoader(LOADER_MAIN, null, this);
    }

    public void show(List<com.github.hubreader.Post> result) {

        PostsAdapter adapter = new PostsAdapter(result, this);

        ListView listView = (ListView) findViewById(R.id.news);
        listView.setAdapter(adapter);
        NewsProvider.news = result;

        ProgressBar loader = (ProgressBar) findViewById(R.id.loader);
        loader.setVisibility(View.GONE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case LOADER_MAIN:
                return new PostLoader(this, PostProvider.URI_POSTS);
            case LOADER_NEW:
                return new PostLoader(this, PostProvider.URI_NEW_POSTS);

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<Post> posts = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            posts.add(PostMapper.toPost(cursor));
        }
        show(posts);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void touchEvents() {

        ImageView button = (ImageView) findViewById(R.id.btn_update);

        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                showNew();
            }
        });
    }

    private void showNew() {
        Toast.makeText(this, "Update ...", Toast.LENGTH_SHORT).show();
        getLoaderManager().initLoader(LOADER_NEW, null, this);
    }
}