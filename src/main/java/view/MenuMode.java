package view;

import controller.WebSourceController;
import controller.WordController;
import exception.AppException;
import model.entity.Word;
import services.HtmlWordsHelper;
import services.HttpClient;
import services.HttpResponse;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuMode {
    private static final Logger log = Logger.getLogger(MenuMode.class.getName());

    private static final WebSourceController webSourceController = WebSourceController.getInstance();
    private static final WordController wordController = WordController.getInstance();
    private static final Scanner scan = new Scanner(System.in);

    public static void createTables() throws Exception {
        if (webSourceController.createWebSourceTable()) {
            if (wordController.createWordsTable()) {
                log.info("Tables created");
            }
        }
    }

    public static void start() throws Exception {
        HttpResponse httpResponse;

        String url = getStringEnterUrl();
        try {
            httpResponse =  new HttpClient(url).connect();
            httpResponse.checkOk();
        }catch (AppException e) {
            e.getMessage();
            return;
        }
        HtmlWordsHelper wordsHelper = new HtmlWordsHelper();
        wordsHelper.setCharset(httpResponse.getCharset());
        wordsHelper.setWordDelimiters(" ", "\\,", "\\.", "\\!", "\\?","\"", "\\'","\\;", "\\:", "\\[", "\\]", "\\(", "\\)", "\n", "\r", "\t");

        Map<String, Integer> statistics = wordsHelper.getDataStatistic(httpResponse);

        wordController.addWords(statistics, url);
        log.info("Data saved successfully");
    }

    public static void printHistoryByUrl() throws Exception {
        String url = getStringEnterUrl();
        List<Word>  words = wordController.getWordsBySourceName(url);

        log.info("Statistics for " + url + ":" + System.lineSeparator());
        for (Word word: words) {
            print(word.getWord() + ": " + word.getQtyWords());
        }
    }

    public static void deleteWordsByUrl() throws Exception {
        String url = getStringEnterUrl();
        wordController.deleteWordsByUrl(url);
    }

    public static int getInt(String message) {
        print(message);

        String data = scan.nextLine();
        if (!data.matches("\\d+")) {
            throw new AppException("Don't correct mode value: " + data);
        }
        return Integer.parseInt(data);
    }

    public static String getString(String message) {
        print(message);
        return scan.nextLine();
    }

    public static String getStringEnterUrl() {
       return getString("Enter URL:");
    }

    public static void print(String text) {
        System.out.println(text);
    }
}
