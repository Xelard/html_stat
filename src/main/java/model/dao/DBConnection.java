package model.dao;

import exception.DAOException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    private static Connection connect = null;

    public static Connection getConnection() throws DAOException {
            connect = null;
            try {
                Properties properties = loadProperties();
                String url = properties.getProperty("url");
                connect = DriverManager.getConnection(url, properties);
            }
            catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        return connect;
    }

    public static void closeConnection() throws DAOException {
        if (connect != null) {
            try {
                connect.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() throws DAOException {
        try (FileInputStream fs = new FileInputStream(String.valueOf(DBConnection.class.getResource("db.properties")))) {
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        }
        catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st) throws DAOException {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) throws DAOException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        }
    }
}


