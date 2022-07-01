package sqltask.application.methods;

import sqltask.application.MenuTable;

import java.util.ArrayList;
import java.util.List;

public class MethodsMenu {

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
}
