package com.github.almazko.hubreader.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import com.github.almazko.hubreader.PostsCursorAdapter;
import com.github.almazko.hubreader.R;
import com.github.almazko.hubreader.data.PostProvider;
import com.github.almazko.hubreader.data.PostTable;

/**
 * @author Almazko
 */
public class DataActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);

        Cursor cursor = getContentResolver().query(
                PostProvider.URI_POSTS,
                null,
                PostProvider.PARAM_ACTUAL,
                null,
                PostTable._ID + " DESC");
        PostsCursorAdapter adapter = new PostsCursorAdapter(this, cursor);

        ListView listView = (ListView) findViewById(R.id.data);
        listView.setAdapter(adapter);
    }
}