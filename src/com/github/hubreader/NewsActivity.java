package com.github.hubreader;

import android.app.Activity;
import android.os.Bundle;
import com.github.hubreader.task.HtmlPageReader;

/**
 * @author Almazko
 */
public class NewsActivity extends Activity {

    private Post news;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        Bundle extras = getIntent().getExtras();
        int i = extras.getInt(Post.ID);

        news = NewsProvider.news.get(i);

        HtmlPageReader task = new HtmlPageReader(this, news.shortLink);
        task.execute();

    }

    public void showPage(String result) {
//        TextView content = (TextView) findViewById(R.id.content);
//        content.setText(Html.fromHtml(result));


    }
}