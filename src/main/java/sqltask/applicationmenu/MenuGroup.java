package sqltask.applicationmenu;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("java:S106")
public class MenuGroup implements Menu {

    private final String label;
    private final List<Menu> items;

    public MenuGroup(String label, Menu... items) {
        this.label = label;
        this.items = Arrays.asList(items);
    }

    @Override
    public String getLabel() {
        return label;
    }

    public MenuGroup completeMenu(MenuHandler menuHandler) {
            return new MenuGroup("MAIN MENU",
                    new MenuGroup("GROUPS OPTIONS", new MenuItem("Compare group", menuHandler::compareGroup)),
                    new MenuGroup("COURSES OPTIONS",
                            new MenuItem("Find students by Course", menuHandler::findStudentsByCourse),
                            new MenuItem("Add course to Student", menuHandler::setNewCourse),
                            new MenuItem("Unlink Course", menuHandler::unlinkCourse)),
                    new MenuGroup("STUDENTS OPTIONS",
                            new MenuItem("Add new student", menuHandler::addStudent),
                            new MenuItem("Delete student", menuHandler::deleteStudent)
                    )
            );
    }

    @Override
    public void doAction() {

        Scanner sc = new Scanner(System.in);
        int action;
        do {
            int index = 0;
            System.out.println(label);
            for (Menu item : items) {
                System.out.println((++index) + ". " + item.getLabel());
            }
            System.out.println("Print 0 to exit.");
            action = sc.nextInt();
            if (action != 0) {
                items.get(action - 1).doAction();
            }
        } while (action != 0);
    }
}
