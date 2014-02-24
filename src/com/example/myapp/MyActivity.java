package com.example.myapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.example.myapp.data.PostLoader;
import com.example.myapp.task.RssReader;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    RssReader task;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getLoaderManager().initLoader(0, null, this);

    }

    public void showData(List<com.example.myapp.Post> result) {

        PostsAdapter adapter = new PostsAdapter(result, this);

        ListView listView = (ListView) findViewById(R.id.news);
        listView.setAdapter(adapter);
        NewsProvider.news = result;

        ProgressBar loader = (ProgressBar) findViewById(R.id.loader);
        loader.setVisibility(View.GONE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new PostLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        ArrayList<Post> posts = new ArrayList<>();
        while (cursor.moveToNext()) {
            posts.add(PostMapper.toPost(cursor));
        }
        showData(posts);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
