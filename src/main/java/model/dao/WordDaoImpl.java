package model.dao;

import exception.DAOException;
import model.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WordDaoImpl implements WordDao {

    public boolean createWordsTable() throws DAOException {
        Connection connection;
        ResultSet resultSet;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement
                    ("CREATE TABLE Words " +
                            "(Id SERIAL PRIMARY KEY, Word VARCHAR(255), " +
                            "Qty INTEGER, " +
                            "SourceId INTEGER, " +
                            "FOREIGN KEY (SourceId) REFERENCES WebSources (Id))");
            resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.closeConnection();
        }
    }

    @Override
    public void insert(List<Word> words) throws DAOException {
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO Words (word, qty, sourceId) VALUES (?, ?, ?)");
            for(Word word : words) {
                statement.setString(1, word.getWord());
                statement.setInt(2, word.getQtyWords());
                statement.setInt(3, word.getWebSource().getId());
                statement.addBatch();
            }
            int[] rows = statement.executeBatch();
            if (rows.length == 0) {
                throw new DAOException("No rows affected!");
            }
        } catch (SQLException e) {
            throw new DAOException(e.getNextException().getMessage());
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.closeConnection();
        }

    }

    @Override
    public void deleteWordsBySource(WebSource source) throws DAOException {
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("DELETE FROM Words WHERE SourceID = ?");
            statement.setInt(1, source.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.closeConnection();
        }
    }

    private Word createWord(ResultSet resultSet, WebSource source) throws SQLException {
        source.setId(resultSet.getInt("SourceId"));

        Word word = new Word();
        word.setId(resultSet.getInt("Id"));
        word.setWord(resultSet.getString("Word"));
        word.setQtyWords(resultSet.getInt("Qty"));

        word.setWebSource(source);
        return word;
    }

    @Override
    public List<Word> getDataBySource(WebSource source) throws DAOException {
        Connection connection;
        ResultSet resultSet;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(
                    "SELECT Words.* FROM WebSources INNER JOIN Words ON WebSources.Id = Words.SourceId WHERE WebSources.Name = ?"
            );
            statement.setString(1, source.getName());
            resultSet = statement.executeQuery();

            List<Word> list = new ArrayList<>();
            while (resultSet.next()) {
                Word word = createWord(resultSet, source);
                list.add(word);
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.closeConnection();
        }
    }

}
