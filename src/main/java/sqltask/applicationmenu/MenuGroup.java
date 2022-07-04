package sqltask.applicationmenu;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("java:S106")
public class MenuGroup implements Menu {

    String label;
    List<Menu> items;

    public MenuGroup(String label, Menu... items) {
        this.label = label;
        this.items = Arrays.asList(items);
    }

    @Override
    public String getLabel() {
        return label;
    }

   static public MenuGroup completeMenu(Controller controller) {
        return new MenuGroup("MAIN MENU",
                new MenuGroup("GROUPS OPTIONS", new MenuItem("Compare group", controller::compareGroup)),
                new MenuGroup("COURSES OPTIONS",
                        new MenuItem("Find students by Course", controller::findStudentsByCourse),
                        new MenuItem("Add course to Student", controller::setNewCourse),
                        new MenuItem("Unlink Course", controller::unlinkCourse)),
                new MenuGroup("STUDENTS OPTIONS",
                        new MenuItem("Add new student", controller::addStudent),
                        new MenuItem("Delete student", controller::deleteStudent)
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
