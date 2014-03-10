package com.github.hubreader.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ImageLoader extends AsyncTask<URL, Void, Bitmap> {
    private final View loaderView;
    ImageView imageView;

    public ImageLoader(ImageView image, View loader) {
        this.imageView = image;
        this.loaderView = loader;
    }

    protected Bitmap doInBackground(URL... urls) {
        URL url = urls[0];
        Bitmap image = null;

        try {
            InputStream in = url.openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    protected void onPostExecute(Bitmap result) {
        if (imageView != null) {
            loaderView.setVisibility(View.GONE);
            imageView.setImageBitmap(result);
        }
    }
}
