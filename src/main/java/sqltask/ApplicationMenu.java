package sqltask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.*;

@SuppressWarnings("java:S106")
public class ApplicationMenu {

    ApplicationMethods appMethods = new ApplicationMethods();
    Class cls = appMethods.getClass();

    public List<MenuTable> getMenuTable() {

        List<MenuTable> menuTable = new ArrayList<>();
        menuTable.add(new MenuTable(1, "COMPARE GROUP WITH OTHERS", "compareGroup"));
        menuTable.add(new MenuTable(2, "FIND STUDENTS FROM COURSE","findStudentsByCourse"));
        menuTable.add(new MenuTable(3, "CREATE NEW STUDENTS", "putNewStudent"));
        menuTable.add(new MenuTable(4, "DELETE STUDENT", "deleteStudent"));
        menuTable.add(new MenuTable(5, "SET NEW COURSE TO STUDENT", "giveCourseToStudent"));
        menuTable.add(new MenuTable(6, "UNLINK COURSE FROM STUDENT", "unlinkCourse"));
        return menuTable;
    }

    public String menuOutput(List<MenuTable> menuTable) {

        StringJoiner sj = new StringJoiner("");
        sj.add(System.lineSeparator());
        sj.add(Utils.stringCentre("APPLICATION_NAME",30) + System.lineSeparator());
        sj.add(System.lineSeparator());
        sj.add("APPLICATION FUNCTIONS:" + System.lineSeparator());
        for (MenuTable mt : menuTable) {
            sj.add(mt.getCode() + ". " + mt.getName() + ": " + mt.getMethodName());
            sj.add(System.lineSeparator());
        }
        return sj.toString();
    }

    public void implementMenu(List<MenuTable> menuTable, Object cls) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Scanner sc = new Scanner(System.in);
        System.out.println(menuOutput(menuTable));
        System.out.println("To USE app function ENTER number of FUNCTION bellow: ");
        int functionNum = sc.nextInt();
        for (MenuTable menuTab : menuTable) {
            if (menuTab.getCode() == functionNum) {
                Method method = cls.getClass().getDeclaredMethod(menuTab.getMethodName(), null);
                method.invoke(cls, null);
            }
        }
    }
}
