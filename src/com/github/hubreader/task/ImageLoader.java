package com.github.hubreader.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ImageLoader extends AsyncTask<URL, Void, Bitmap> {
    ImageView bmImage;

    public ImageLoader(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(URL... urls) {
        URL url = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = url.openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if (bmImage != null) bmImage.setImageBitmap(result);
    }
}
