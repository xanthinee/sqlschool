package sqltask.applicationmenu;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Method;

@SuppressWarnings("java:S106")
public class MenuGroup implements Menu {

    private final String label;
    private final List<Menu> menuList;

    public MenuGroup(String label) {
        this.label = label;
        this.menuList = new ArrayList<>();
    }

    @Override
    public String getLabel() {
        return label;
    }


    public void addItem(Menu menuItem) {
            menuList.add(menuItem);
    }

    @Override
    public void doAction() {

        Scanner sc = new Scanner(System.in);
        int action;
        do {
            int index = 0;
            System.out.println(label);
            for (Menu item : menuList) {
                System.out.println(index + 1 + ". " + item.getLabel());
                index++;
            }
            System.out.println("Enter number of method to continue");
            System.out.println("Print 0 to exit.");
            action = sc.nextInt();
            if (action != 0) {
                menuList.get(action - 1).doAction();
            }
        } while (action != 0);
    }
}
