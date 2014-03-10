package com.github.hubreader;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Almazko
 */
public class AndroidSaxFeedParser extends BaseFeedParser {


    private static final SimpleDateFormat FORMATTER;

    static {
        FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        FORMATTER.setDateFormatSymbols(DateFormatSymbols.getInstance(Locale.ENGLISH));
    }

    public AndroidSaxFeedParser(String feedUrl) {
        super(feedUrl);
    }

    public List<Post> parse() {
        final Post currentPost = new Post();
        RootElement root = new RootElement("rss");
        final List<Post> posts = new ArrayList<Post>();

        Element channel = root.getChild("channel");
        Element item = channel.getChild("item");
        item.setEndElementListener(new EndElementListener() {
            public void end() {
                posts.add(currentPost.copy());
            }
        });
        item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                currentPost.setTitle(body);
            }
        });
        item.getChild(LINK).setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                int id = Integer.parseInt(body.substring(25, body.length() - 1));
                currentPost.setId(id);
            }
        });

        item.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                currentPost.setDescription(body);
            }
        });

        item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                try {
                    currentPost.setPublishDate(FORMATTER.parse(body.trim()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8,
                    root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return posts;
    }
}