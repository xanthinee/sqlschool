package sqltask.applicationmenu;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.reflect.Method;

@SuppressWarnings("java:S106")
public class MenuGroup implements Menu {

    private final String label;
    private final LinkedList<Object> items;

    public MenuGroup(String label) {
        this.label = label;
        this.items = new LinkedList<>();
    }

    @Override
    public String getLabel() {
        return label;
    }

//    public MenuGroup completeMenu(MenuHandler menuHandler) {
//        return new MenuGroup("MAIN MENU",
//                new MenuGroup("GROUPS OPTIONS", new MenuItem("Compare group", menuHandler::compareGroup)),
//                new MenuGroup("COURSES OPTIONS",
//                        new MenuItem("Find students by Course", menuHandler::findStudentsByCourse),
//                        new MenuItem("Add course to Student", menuHandler::setNewCourse),
//                        new MenuItem("Unlink Course", menuHandler::unlinkCourse)),
//                new MenuGroup("STUDENTS OPTIONS",
//                        new MenuItem("Add new student", menuHandler::addStudent),
//                        new MenuItem("Delete student", menuHandler::deleteStudent)
//                )
//            );
//    }

    public void addItem(Object menuItem) {
        try {
            items.add(menuItem);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction() {

        Scanner sc = new Scanner(System.in);
        int action;
        do {
            int index = 0;
            System.out.println(label);
            for (Object item : items) {
                try {
                    Method method = item.getClass().getDeclaredMethod("getLabel", null);
                    System.out.println((++index) + ". " + method.invoke(item, null));
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Enter number of method to continue");
            System.out.println("Print 0 to exit.");
            action = sc.nextInt();
            if (action != 0) {
                try {
                    Method method =  items.get(action - 1).getClass().getDeclaredMethod("doAction", null);
                    method.invoke(items.get(action - 1), null);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } while (action != 0);
    }
}
