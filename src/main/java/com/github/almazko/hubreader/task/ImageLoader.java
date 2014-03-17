package com.github.almazko.hubreader.task;

import android.graphics.*;
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
            image = resize(image, 200, 200);
            image = getRoundedCornerBitmap(image, 2);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 95, bos);
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
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public static Bitmap resize(Bitmap bm, int newHeight, int newWidth) {
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);
    }


    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
