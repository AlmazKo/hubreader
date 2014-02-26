package com.github.hubreader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import static com.github.hubreader.BaseFeedParser.*;

/**
 * @author Almazko
 */
public class RssHandler extends DefaultHandler {
    private List<Post> posts;
    private Post currentPost;
    private StringBuilder builder;

    public List<Post> getPosts(){
        return this.posts;
    }
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        super.endElement(uri, localName, name);
        if (this.currentPost != null){
            if (localName.equalsIgnoreCase(TITLE)){
                currentPost.title = builder.toString();
            } else if (localName.equalsIgnoreCase(LINK)){
                currentPost.setLink(builder.toString());
            } else if (localName.equalsIgnoreCase(DESCRIPTION)){
                currentPost.setDescription(builder.toString());
            } else if (localName.equalsIgnoreCase(PUB_DATE)){
                currentPost.setDate(builder.toString());
            } else if (localName.equalsIgnoreCase(ITEM)){
                posts.add(currentPost);
            }
            builder.setLength(0);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        posts = new ArrayList<Post>();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase(ITEM)){
            this.currentPost = new Post();
        }
    }
}