package model.dao;

import exception.DAOException;
import model.entity.*;
import java.sql.*;

public class WebSourceDaoImpl implements SourceDao {

    public boolean createWebSourceTable() throws DAOException {
        boolean result;
        Connection connection;
        ResultSet resultSet;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("CREATE TABLE WebSources" +
                    " (Id SERIAL PRIMARY KEY," +
                    " Name VARCHAR)");
            resultSet = statement.executeQuery();
            result = resultSet.next();
            return result;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.closeConnection();
        }
    }


    @Override
    public void insert(WebSource source) throws DAOException {
        Connection connection;
        ResultSet resultSet;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO WebSources (Name) Values (?)", new String[]{"id"});
            statement.setString(1, source.getName());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                source.setId(resultSet.getInt(1));
            } else {
                throw new DAOException("No rows affected!");
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.closeConnection();
        }
    }

    @Override
    public WebSource getDataByName(String name) throws DAOException {
        Connection connection;
        ResultSet resultSet;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("SELECT * FROM WebSources WHERE Name = ?");
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                WebSource source = new WebSource();
                source.setName(name);
                source.setId(resultSet.getInt("Id"));
                return source;
            }
            return null;
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        } finally {
            DBConnection.closeStatement(statement);
            DBConnection.closeConnection();
        }
    }

}
