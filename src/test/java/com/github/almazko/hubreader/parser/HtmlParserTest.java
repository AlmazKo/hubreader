package com.github.almazko.hubreader.parser;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * @author Almazko
 */
public class HtmlParserTest {

    @Test
    public void findSrcPreview() throws Exception {
        String src = HtmlParser.findSrcPreview(readFile("/parser/post_with_one_image.txt"));
        assertEquals("http://example.com/1.jpg", src);
    }

    @Test
    public void findHardSrcPreview() throws Exception {
        String src = HtmlParser.findSrcPreview(readFile("/parser/post_with_hard_image.txt"));
        assertEquals("http://example.com/2.jpg", src);
    }
    @Test
    public void findSrcPreview1() throws Exception {
        String src = HtmlParser.findSrcPreview(readFile("/parser/html_1.txt"));
        assertEquals("http://habrastorage.org/getpro/habr/post_images/20a/7ed/034/20a7ed0347d07de7da7624d393cc95c8.jpg", src);
    }

    protected String readFile(String name) throws IOException {

        BufferedReader br = null;
        try {
            URL url = getClass().getResource(name);
            br = new BufferedReader(new FileReader(url.getPath()));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return "";

    }
}
