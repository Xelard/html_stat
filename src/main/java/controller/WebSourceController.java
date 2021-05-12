package controller;

import exception.DAOException;
import model.entity.WebSource;
import services.WebSourceService;


@SuppressWarnings("UnnecessaryLocalVariable")
public class WebSourceController {
    private static final WebSourceController webSourceController = new WebSourceController();
    private final WebSourceService webSourceService = WebSourceService.getInstance();

    private WebSourceController() {}

    public static WebSourceController getInstance() {
        return webSourceController;
    }

    public boolean createWebSourceTable() throws DAOException {
        return webSourceService.createWebSourceTable();
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public WebSource getWebSourceByName(String name) throws DAOException {
        WebSource source = webSourceService.getWebSourceByName(name);
        return source;
    }

    public void addWebSource(WebSource source) throws DAOException {
        webSourceService.addWebSource(source);
    }


}
