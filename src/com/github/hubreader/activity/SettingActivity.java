package com.github.hubreader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.github.hubreader.R;
import com.github.hubreader.data.PostProvider;

/**
 * @author Almazko
 */
public class SettingActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        touchEvents();
    }

    private void touchEvents() {
        findViewById(R.id.btn_remove_data).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                removeAllData();
            }
        });

        findViewById(R.id.btn_show_all_data).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                showAllData();
            }
        });
    }

    private void showAllData() {

    }

    private void removeAllData() {
        getContentResolver().delete(PostProvider.URI_POSTS, null, null);
        Toast.makeText(this, "Deleted all data", Toast.LENGTH_SHORT).show();

    }
}