package controller;

import exception.DAOException;
import model.entity.WebSource;
import model.entity.Word;
import services.WordService;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class WordController {
    private static final WordController wordController = new WordController();
    private final WordService wordService = WordService.getInstance();

    private WordController() {}

    public static WordController getInstance() {
        return wordController;
    }

    public boolean createWordsTable() throws DAOException {
        return wordService.createWordsTable();
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<Word> getWordsBySourceName(String name) throws DAOException {
        List<Word> source = wordService.getWordsBySource(new WebSource(name));
        return source;
    }

    public void addWords(Map wordsData, String sourceName) throws DAOException {
        WebSource source = WebSourceController.getInstance().getWebSourceByName(sourceName);
        if (source == null) {
            source = new WebSource(sourceName);
            WebSourceController.getInstance().addWebSource(source);
        } else { wordService.deleteWordsByUrl(source); }

        // Не придумал другой способ обойти ошибку компилятора "cannot resolve method getKey, getValue...."
        Map<String, Integer> tempWordsData = new HashMap<>(wordsData);

        WebSource finalSource = source;
        List<Word> listWord =  tempWordsData.entrySet().stream()
                .map(v -> new Word(v.getKey(), v.getValue(), finalSource))
                .collect(Collectors.toList());

        wordService.addWords(listWord);
    }

    public void deleteWordsByUrl(String nameSource) throws DAOException {
        WebSource source = WebSourceController.getInstance().getWebSourceByName(nameSource);
        if (source != null) {
            wordService.deleteWordsByUrl(source);
        }
    }


}
