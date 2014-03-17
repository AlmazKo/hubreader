package com.github.almazko.hubreader.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.github.almazko.hubreader.*;
import com.github.almazko.hubreader.data.PostLoader;
import com.github.almazko.hubreader.data.PostProvider;
import com.github.almazko.hubreader.task.RssReader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.main_types, android.R.layout.simple_spinner_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_main);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void show(List<Post> result) {

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
        List<Post> posts = new ArrayList<Post>();
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

        ImageView buttonUpdate = (ImageView) findViewById(R.id.btn_update);
        ImageView buttonSettings = (ImageView) findViewById(R.id.btn_setting);

        buttonUpdate.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                showNew();
            }
        });

        buttonSettings.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                showSettings();
            }
        });
    }

    private void showSettings() {
        Intent intent = new Intent();
        intent.setClass(this, SettingActivity.class);
        this.startActivity(intent);
    }

    private void showNew() {
        Toast.makeText(this, "Update ...", Toast.LENGTH_SHORT).show();
        getLoaderManager().initLoader(LOADER_NEW, null, this);
    }
}