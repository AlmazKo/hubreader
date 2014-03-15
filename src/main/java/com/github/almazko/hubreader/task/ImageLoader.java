package com.github.almazko.hubreader.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import com.github.almazko.hubreader.Post;
import com.github.almazko.hubreader.data.Updater;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageLoader extends AsyncTask<Post, Void, Bitmap> {
    private final View loaderView;
    Updater updater;
    ImageView imageView;
    Post post;

    public ImageLoader(Updater updater, ImageView image, View loader) {
        this.updater = updater;
        this.imageView = image;
        this.loaderView = loader;
    }

    protected Bitmap doInBackground(Post... posts) {
        post = posts[0];
        URL url = post.previewLink;
        Bitmap image = null;


        try {
            InputStream in = url.openStream();
            image = BitmapFactory.decodeStream(in);
            image = resize(image, 300, 300);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bos);
            updater.image(post, bos.toByteArray());
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

    public static Bitmap resize(Bitmap bm, int newHeight, int newWidth) {
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);
    }
}
