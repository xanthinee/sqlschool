package sqltask.application.menu;

import sqltask.application.methods.DeleteStudent;
import sqltask.application.methods.PutNewStudent;
import sqltask.application.methods.UnlinkCourse;
import sqltask.application.methods.comparison.CompareGroups;
import sqltask.application.methods.courseaddition.GiveNewCourse;
import sqltask.application.methods.coursemembers.StudentsByCourse;
import sqltask.helpers.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

@SuppressWarnings("java:S106")
public class ApplicationMenu {

    public String menuOutput(List<MenuTable> menuTable) {

        StringJoiner sj = new StringJoiner("");
        sj.add(System.lineSeparator());
        sj.add(Utils.stringCentre("APPLICATION_NAME",30) + System.lineSeparator());
        sj.add(System.lineSeparator());
        sj.add("APPLICATION FUNCTIONS:" + System.lineSeparator());
        for (MenuTable mt : menuTable) {
            sj.add(mt.getCode() + ". " + mt.getName());
            sj.add(System.lineSeparator());
        }
        return sj.toString();
    }

    public void implementMenu(Connection connection) throws SQLException {

        MethodsMenu methods = new MethodsMenu();
        List<MenuTable> listOfMethods = methods.getMenuTable();
        Scanner sc = new Scanner(System.in);
        System.out.println(menuOutput(listOfMethods));
        System.out.println("To USE app function ENTER number of FUNCTION bellow: ");
        int functionNum = sc.nextInt();

        switch (functionNum) {
            case 1 :
                CompareGroups compareGps = new CompareGroups();
                compareGps.compareGroup(connection);
                break;
            case 2 :
                StudentsByCourse courseStd = new StudentsByCourse();
                courseStd.findStudentsByCourse(connection);
                break;
            case 3 :
                PutNewStudent newStd = new PutNewStudent();
                newStd.putNewStudent(connection);
                break;
            case 4 :
                DeleteStudent deleteStd = new DeleteStudent();
                deleteStd.deleteStudent(connection);
                break;
            case 5 :
                GiveNewCourse newCourse = new GiveNewCourse();
                newCourse.giveCourseToStudent(connection);
                break;
            case 6 :
                UnlinkCourse unlink = new UnlinkCourse();
                unlink.unlinkCourse(connection);
                break;

            default:
                throw new IllegalStateException("Enter correct NUMBER of application method!");
        }

    }
}
