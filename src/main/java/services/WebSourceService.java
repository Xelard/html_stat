package services;

import exception.DAOException;
import model.dao.SourceDao;
import model.dao.WebSourceDaoImpl;
import model.entity.WebSource;

public class WebSourceService {

    private static final WebSourceService webSourceService = new WebSourceService();
    private final SourceDao sourceDao;

    private WebSourceService() {
        sourceDao = new WebSourceDaoImpl();
    }

    public static WebSourceService getInstance() {
        return webSourceService;
    }

    public boolean createWebSourceTable() throws DAOException {
        return sourceDao.createWebSourceTable();
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public WebSource getWebSourceByName(String name) throws DAOException {
        WebSource sourcesDao = sourceDao.getDataByName(name);
        return sourcesDao;
    }

    public void addWebSource(WebSource source) throws DAOException {
        sourceDao.insert(source);
    }



}

