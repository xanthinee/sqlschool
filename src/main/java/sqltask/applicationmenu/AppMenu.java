package sqltask.applicationmenu;

import java.util.List;
import java.util.Scanner;

@SuppressWarnings("java:S106")
public class AppMenu implements Menu {

    private final String label;
    private final List<Menu> items;

    public AppMenu(String label, List<Menu> items) {
        this.label = label;
        this.items = items;
    }

    @Override
    public String getLabel() {
        return label;
    }


    public void addItem(Menu menuItem) {
            items.add(menuItem);
    }

    @Override
    public void doAction() {

        Scanner sc = new Scanner(System.in);
        int action;
        do {
            int index = 0;
            System.out.println(label);
            for (Menu item : items) {
                System.out.println(index + 1 + ". " + item.getLabel());
                index++;
            }
            System.out.println("Enter number of method to continue");
            System.out.println("Print 0 to exit.");
            action = sc.nextInt();
            if (action != 0) {
                items.get(action - 1).doAction();
            }
        } while (action != 0);
    }
}
