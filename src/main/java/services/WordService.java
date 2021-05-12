package services;

import exception.DAOException;
import model.dao.WordDao;
import model.dao.WordDaoImpl;
import model.entity.WebSource;
import model.entity.Word;

import java.util.List;

public class WordService {
    private static final WordService wordService = new WordService();
    private final WordDao wordDao;

    private WordService() {
        wordDao = new WordDaoImpl();
    }

    public static WordService getInstance() {
        return wordService;
    }

    public boolean createWordsTable() throws DAOException {
        return wordDao.createWordsTable();
    }
    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<Word> getWordsBySource(WebSource source) throws DAOException {
        List<Word> words = wordDao.getDataBySource(source);
        return words;
    }

    public void addWords(List<Word> words) throws DAOException {
        wordDao.insert(words);
    }

    public void deleteWordsByUrl(WebSource source) throws DAOException {
        wordDao.deleteWordsBySource(source);
    }

}
