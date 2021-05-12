package model.dao;

import exception.DAOException;
import model.entity.*;

public interface SourceDao<T> {

    boolean createWebSourceTable() throws DAOException;
    void insert(WebSource obj) throws DAOException;

    WebSource getDataByName(String url) throws DAOException;


}