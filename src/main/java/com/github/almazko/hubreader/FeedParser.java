package com.github.almazko.hubreader;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

/**
 * @author Almazko
 */
public interface FeedParser {
    List<Post> parse() throws IOException, SAXException;
}
