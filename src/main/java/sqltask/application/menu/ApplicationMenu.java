package sqltask.application.menu;

import sqltask.helpers.Utils;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

@SuppressWarnings("java:S106")
public class ApplicationMenu {

    private String menuOutput() {

        List<Menu> menu = MenuList.menu;
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add(Utils.stringCentre("APPLICATION_NAME",30));
        sj.add("APPLICATION FUNCTIONS:");
        int index = 1;
        for (Menu menuItem : menu) {
            sj.add(index + ". " + menuItem.getMenuText());
            index++;
        }
        return sj.toString();
    }

    public Object implementMenu(Connection connection) {

        List<Menu> menu = MenuList.menu;
        Scanner sc = new Scanner(System.in);
        System.out.println(menuOutput());
        System.out.println("To USE app function ENTER number of FUNCTION bellow: ");
        int functionNum = sc.nextInt();
        return menu.get(functionNum - 1).doAction(connection);
        }
}
