package com.example.myapp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import com.example.myapp.data.MyContentProvider;
import com.example.myapp.data.Posts;
import com.example.myapp.task.RssReader;

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
        getContentResolver().query(
                MyContentProvider.CONTENT_URI,   // The content URI of the words table
                null,                        // The columns to return for each row
                null,                    // Selection criteria
                null,                     // Selection criteria
                null);

        ContentValues values = new ContentValues();
        values.put(Posts.LINK, "http://blabla");
        values.put(Posts.TITLE, "lalala!!");

        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);


        Cursor cursor= getContentResolver().query(
                uri,   // The content URI of the words table
                null,                        // The columns to return for each row
                null,                    // Selection criteria
        null,                     // Selection criteria
                null);

        int cnt = cursor.getCount();

        while (cursor.moveToNext()) {
            String title = cursor.getString(0);
            String title1 = cursor.getString(1);
            String title2 = cursor.getString(2);
        }

        RssReader task = new RssReader(this);
        task.execute();
    }

    public void showData(List<Post> result) {

        PostsAdapter adapter = new PostsAdapter(result, this);

        ListView listView = (ListView) findViewById(R.id.news);
        listView.setAdapter(adapter);
        NewsProvider.news = result;

    }
}
