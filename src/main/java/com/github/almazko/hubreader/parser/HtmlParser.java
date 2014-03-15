package com.github.almazko.hubreader.parser;

/**
 * @author Almazko
 */
public class HtmlParser {
    private static final String IMG_PATTERN = "<img src=\"";

    public static String findSrcPreview(final String string) {
        int pos = string.indexOf(IMG_PATTERN);
        if (pos < 0) {
            return null;
        }
        int startPosSrc = pos + IMG_PATTERN.length();
        int endPosSrs = string.indexOf("\"", pos + IMG_PATTERN.length());

        return string.substring(startPosSrc, endPosSrs);
    }
}
