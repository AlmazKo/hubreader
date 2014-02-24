package com.example.myapp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.example.myapp.data.PostProvider;
import com.example.myapp.task.RssReader;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {
    RssReader task;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Cursor cursor = getContentResolver().query(PostProvider.CONTENT_URI, null, null, null, null);

        ArrayList<Post> posts = new ArrayList<>();

        while (cursor.moveToNext()) {
            posts.add(PostMapper.toPost(cursor));
        }
        showData(posts);

//        RssReader task = new RssReader(this);
//        task.execute();
    }

    public void showData(List<com.example.myapp.Post> result) {

        PostsAdapter adapter = new PostsAdapter(result, this);

        ListView listView = (ListView) findViewById(R.id.news);
        listView.setAdapter(adapter);
        NewsProvider.news = result;

        ProgressBar loader = (ProgressBar) findViewById(R.id.loader);
        loader.setVisibility(View.GONE);
    }
}
