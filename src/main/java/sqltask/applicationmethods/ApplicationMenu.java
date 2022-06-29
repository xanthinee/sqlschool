package sqltask.applicationmethods;

import sqltask.helpers.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

@SuppressWarnings("java:S106")
public class ApplicationMenu {

    public List<MenuTable> getMenuTable(Object cls) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = cls.getClass().getDeclaredMethod("getMenuTable", null);
        return (List<MenuTable>) method.invoke(cls, null);
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
