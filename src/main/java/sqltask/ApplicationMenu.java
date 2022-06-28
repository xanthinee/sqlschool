package sqltask;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.StringJoiner;

public class ApplicationMenu {

    private String throwMenu() {

        StringJoiner sj = new StringJoiner("");
        sj.add(System.lineSeparator());
        sj.add(Utils.stringCentre("APPLICATION_NAME",30) + System.lineSeparator());
        sj.add(System.lineSeparator());
        sj.add("APPLICATIONS FUNCTIONS:" + System.lineSeparator());
        sj.add("1. Compare GROUP with other GROUPS by amount of STUDENTS" + System.lineSeparator());
        sj.add("2. Find STUDENT from exact COURSE" + System.lineSeparator());
        sj.add("3. PUT new STUDENT to SQLTable" + System.lineSeparator());
        sj.add("4. DELETE STUDENT from TABLES" + System.lineSeparator());
        sj.add("5. SET new COURSE to STUDENT" + System.lineSeparator());
        sj.add("6. UNLINK Course from STUDENT" + System.lineSeparator());
        return sj.toString();
    }

    public void implementMenu() throws SQLException {

        Scanner sc = new Scanner(System.in);
        ApplicationMethods methods = new ApplicationMethods();
        System.out.println(throwMenu());
        System.out.println("To USE app function ENTER number of FUNCTION bellow: ");
        int functionNum = sc.nextInt();
        switch (functionNum)  {
            case 1:
                methods.compareGroup();
                break;
            case 2:
                methods.findStudentsByCourse();
                break;
            case 3:
                methods.putNewStudent();
                break;
            case 4:
                methods.deleteStudent();
                break;
            case 5:
                methods.giveCourseToStudent();
                break;
            case 6:
                methods.unlinkCourse();
                break;
        }
    }
}
