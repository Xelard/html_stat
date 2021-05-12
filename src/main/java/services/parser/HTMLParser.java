package services.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class HTMLParser {
    private static final Logger log = Logger.getLogger(HTMLParser.class.getName());
    private static final List<Tag> IGNORE_TEXT_TAGS = Arrays.asList(HTML.Tag.TITLE, HTML.Tag.STYLE);

    public HTMLParser() {
    }

    public void parse(InputStream inputStream, String charset, ParseListener listener) throws IOException {
        long startTime = System.currentTimeMillis();
        log.info("Parsing html...");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            new ParserDelegator().parse(reader, new HTMLEditorKit.ParserCallback() {
                private HTML.Tag curTag = null;

                @Override
                public void handleText(char[] data, int pos) {
                    super.handleText(data, pos);
                    if (IGNORE_TEXT_TAGS.contains(curTag))
                        return;

                    listener.text(data);
                }

                @Override
                public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
                    super.handleStartTag(t, a, pos);
                    curTag = t;
                }

                @Override
                public void handleEndTag(HTML.Tag t, int pos) {
                    super.handleEndTag(t, pos);
                    curTag = null;
                }

                @Override
                public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
                    super.handleSimpleTag(t, a, pos);
                    curTag = t;
                }
            }, true);
        }

        log.info("Html parse finished (" + (System.currentTimeMillis() - startTime) + " ms).");
    }

    public interface ParseListener {
        void text(char[] symbol);
    }
}
