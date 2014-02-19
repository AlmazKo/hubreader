package com.example.myapp;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Almazko
 */
public class Post implements Comparable<Post> {
    static SimpleDateFormat FORMATTER;
    public static final String ID = "news_id";
    public String title;
    public URL link;
    public URL shortLink;
    public URL imageLink;
    public String description;
    public Date date;


    static {
        FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        FORMATTER.setDateFormatSymbols(DateFormatSymbols.getInstance(Locale.ENGLISH));
    }

    // getters and setters omitted for brevity
    public void setLink(String link) {
        try {
            this.link = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    public String getDate() {
        return FORMATTER.format(this.date);
    }

    public void setDate(String date) {
        try {
            this.date = FORMATTER.parse(date.trim());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // sort by date
    public int compareTo(Post another) {
        if (another == null) return 1;
        // sort descending, most recent first
        return another.date.compareTo(date);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Post copy() {
        Post post = new Post();
        post.date = (Date) date.clone();
        post.title = title;
        post.description = description;
        post.link = link;
        post.shortLink = shortLink;
        post.imageLink = imageLink;

        return post;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortLink(String shortLink) {
        try {
            this.shortLink = new URL(shortLink);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
