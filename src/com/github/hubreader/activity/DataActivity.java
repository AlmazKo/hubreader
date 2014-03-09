package com.github.hubreader.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import com.github.hubreader.PostsCursorAdapter;
import com.github.hubreader.R;
import com.github.hubreader.data.PostProvider;

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
                null);
        PostsCursorAdapter adapter = new PostsCursorAdapter(this, cursor);

        ListView listView = (ListView) findViewById(R.id.data);
        listView.setAdapter(adapter);
    }
}