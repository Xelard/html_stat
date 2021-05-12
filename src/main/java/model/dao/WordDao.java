package model.dao;

import exception.DAOException;
import model.entity.*;
import java.util.List;

public interface WordDao {

    boolean createWordsTable() throws DAOException;
    void insert(List<Word> word) throws DAOException;

    void deleteWordsBySource(WebSource source) throws DAOException;
    List<Word> getDataBySource(WebSource source) throws DAOException;


}
