package com.example.myapp.task;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import com.example.myapp.NewsActivity;
import com.example.myapp.R;

import java.io.IOException;
import java.net.URL;

/**
 * @author Almazko
 */
public class HtmlPageReader extends AsyncTask<String, Integer, String> {

    private NewsActivity activity;
    private final URL link;

    public HtmlPageReader(NewsActivity activity, URL link) {

        this.activity = activity;
        this.link = link;
    }

    @Override
    protected String doInBackground(String... strings) {

        return getPage();
    }


    protected void onPostExecute(String result) {
        if (result != null) {
            activity.showPage(result);


            WebView webview = (WebView) activity.findViewById(R.id.content);
            webview.loadData(result, "text/html; charset=utf-8", "utf-8");
        } else {
            Log.v("sss_err", "error task");
        }

    }


    private String getPage() {
        try {
            return convertStreamToString(link.openConnection().getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
