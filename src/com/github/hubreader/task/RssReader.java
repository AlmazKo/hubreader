package com.github.hubreader.task;

import android.os.AsyncTask;
import android.util.Log;
import com.github.hubreader.AndroidSaxFeedParser;
import com.github.hubreader.Post;
import com.github.hubreader.MyActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author Almazko
 */
public class RssReader extends AsyncTask<String, Integer, List<Post>> {

    private static final String IMG_PATTERN = "<img src=\"";
    private MyActivity activity;

    public RssReader(MyActivity activity) {

        this.activity = activity;
    }

    @Override
    protected List<Post> doInBackground(String... strings) {

        return getNews();
    }


    protected void onPostExecute(List<Post> result) {


        for (Post post : result) {
            try {
                String src = findSrcPreview(post.description);
                if (src != null) {
                    post.imageLink = new URL(src);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        if (result != null) {
//            activity.showData(result);
        } else {
            Log.v("sss_err", "error task");
        }

    }


    private List<Post> getNews() {

        try {
            AndroidSaxFeedParser parser = new AndroidSaxFeedParser("http://habrahabr.ru/rss/hubs/");

            return parser.parse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String findSrcPreview(final String string) {
        int pos = string.indexOf(IMG_PATTERN);
        if (pos < 0) {
            return null;
        }
        int startPosSrc = pos + IMG_PATTERN.length();
        int endPosSrs = string.indexOf("\"", pos + IMG_PATTERN.length());

        return string.substring(startPosSrc, endPosSrs);
    }
}
