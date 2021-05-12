package view;

import java.util.logging.Logger;

public class MainMenu {
    private static final Logger log = Logger.getLogger(MainMenu.class.getName());

    public void mainMenu() throws Exception {
        int mode;

        displayMenu();
        mode = MenuMode.getInt("Enter number:");

        switch (mode) {
            case 0:
                MenuMode.createTables();
                mainMenu();
                break;
            case 1:
                MenuMode.start();
                mainMenu();
                break;
            case 2:
                MenuMode.printHistoryByUrl();
                mainMenu();
                break;
            case 3:
                MenuMode.deleteWordsByUrl();
                mainMenu();
                break;
            case 4:
                return;
            default:
                log.warning("Unknown app mode");
        }
    }

    public static void displayMenu() {
        System.out.println("============================================");
        System.out.println("Select app mode:");
        System.out.println("0 - Create tables");
        System.out.println("1 - Run parse from url");
        System.out.println("2 - Print history from DB by key (url)");
        System.out.println("3 - Delete data from DB by key (url)");
        System.out.println("4 - Exit");
        System.out.println("============================================");
    }

}
