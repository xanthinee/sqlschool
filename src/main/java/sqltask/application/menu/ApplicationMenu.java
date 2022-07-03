package sqltask.application.menu;

import sqltask.helpers.Utils;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.*;

@SuppressWarnings("java:S106")
public class ApplicationMenu {

    public String menuOutput() {

        List<IMenu> menu = MenuList.menu;
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add(Utils.stringCentre("APPLICATION_NAME",30));
        sj.add("APPLICATION FUNCTIONS:");
        int index = 1;
        for (IMenu menuItem : menu) {
            sj.add(index + ". " + menuItem.getMenuText());
            index++;
        }
        return sj.toString();
    }

    public Object implementMenu(Connection connection) {

        List<IMenu> menu = MenuList.menu;
        Map<Integer, IMenu> menuMap = new HashMap<>();
        int index = 1;
        for (IMenu menuItem : menu) {
            menuMap.put(index, menuItem);
            index++;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println(menuOutput());
        System.out.println("To USE app function ENTER number of FUNCTION bellow: ");
        int functionNum = sc.nextInt();
        return menuMap.get(functionNum).doAction(connection);
        }
}
