package com.github.hubreader;

import android.graphics.Bitmap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * @author Almazko
 */
public class Post implements Comparable<Post> {

    public static final String HABR_FULL = "http://habrahabr.ru/post/";
    public static final String HABR_MOBILE = "http://m.habrahabr.ru/post/";

    public static final String ID = "id";

    public int id;
    public Date publishDate;
    public String title;
    public String description;
    public URL previewLink;
    public Bitmap preview;


    public URL getLink() {
        try {
            return new URL(HABR_MOBILE + id + "/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URL getPreviewLink() {
        return previewLink;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    // sort by publishDate
    public int compareTo(Post another) {
        if (another == null) return 1;
        return Double.compare(another.id, id);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post copy() {
        Post post = new Post();
        post.id = id;
        post.title = title;
        post.publishDate = (Date) publishDate.clone();
        post.description = description;

        post.previewLink = previewLink;

        return post;
    }
}
