package sqltask.application.menu;

import java.util.ArrayList;
import java.util.List;

public class MethodsMenu {

    public List<MenuTable> getMenuTable() {

        List<String> menuTable = List.of("COMPARE GROUP WITH OTHERS", "FIND STUDENTS FROM COURSE", "CREATE NEW STUDENT",
                "DELETE STUDENT", "SET NEW COURSE TO STUDENT", "UNLINK COURSE FROM STUDENT");
        int index = 1;
        List<MenuTable> appMenu = new ArrayList<>();
        for (String name : menuTable) {
            appMenu.add(new MenuTable(index, name));
            index++;
        }
        return appMenu;
    }
}
