package com.github.almazko.hubreader.parser;

/**
 * @author Almazko
 */
public class HtmlParser {
    private static final String IMG_PATTERN = "<img";

    public static String findSrcPreview(final String string) {
        int pos = string.indexOf(IMG_PATTERN);
        if (pos < 0) {
            return null;
        }

        int startPosSrc = string.indexOf("src=\"", pos);
        startPosSrc +=5;
        int endPosSrs = string.indexOf("\"", startPosSrc);

        return string.substring(startPosSrc, endPosSrs);
    }
}
