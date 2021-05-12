package services;

import services.parser.HTMLParser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class HtmlWordsHelper {
    private static final Logger log = Logger.getLogger(HtmlWordsHelper.class.getName());

    private Pattern wordDelimiterPattern;
    private String charset;

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setWordDelimiters(String... delimiters) {
        this.wordDelimiterPattern = createDelimiterPattern(delimiters);
    }

    private static Pattern createDelimiterPattern(String... delimiters) {
        String pattern;
        if (delimiters.length == 0) {
            pattern = "\\s";
        } else {
            pattern = String.join("|", delimiters);
        }

        return Pattern.compile(pattern);
    }

    public Map<String, Integer> getDataStatistic(HttpResponse response) {
        Map<String, Integer> statistics = new HashMap<>();
        try {
            InputStream inputStream = null;
            try {
                inputStream = response.getConnection().getInputStream();

                if (inputStream == null)
                    throw new IllegalStateException("Response has no input");

                new HTMLParser().parse(inputStream, charset, data -> {
                    try (Scanner scanner = new Scanner(new String(data))) {
                        applyDelimiter(scanner, statistics);
                    }
                });
            } finally {
                if (inputStream != null)
                    inputStream.close();
            }
        } catch (Throwable e) {
            log.log(Level.SEVERE, "Something went wrong O_o", e);
        }
        return statistics;
    }


    private void applyDelimiter(Scanner scanner, Map<String, Integer> statistics) {
        String word;
        scanner.useDelimiter(wordDelimiterPattern);
        while (scanner.hasNext()) {
            word = scanner.next().trim();
            if (word.isEmpty())
                continue;

            Integer count = statistics.getOrDefault(word, 0);
            statistics.put(word, ++count);
        }
    }





}