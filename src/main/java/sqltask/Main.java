package sqltask;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class Main {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        /** running .sql script
        try {
            executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
         **/

/** creating table with groups
        GroupsTable groupsTable = new GroupsTable();
        groupsTable.putGroupIntoTable();
        System.out.println(groupsTable.printGroupsTable());
        System.out.println("---------------------------------------------");
 **/

/** creating table with courses
        CoursesTable coursesT = new CoursesTable();
        List<Course> courses = coursesT.makeCoursesList("data/descriptions");
        coursesT.putCoursesInTable(courses);
        System.out.println(coursesT.printCoursesTable());
 **/

/** creating table with students
        StudentsTable st = new StudentsTable();
        List<Student> students = st.finishStudentsCreation();
        st.putStudentsIntoTable(students);
        System.out.println(st.printStudentsTable());
 **/

/** creating student's courses table
        StudentsCoursesTable stCoTab = new StudentsCoursesTable();
        stCoTab.createStdCrsTable();
        System.out.println(stCoTab.printStudCourseTable());
 **/

        ApplicationMethods appMethods = new ApplicationMethods();
        ApplicationMenu menu = new ApplicationMenu();
        List<MenuTable> mt = menu.getMenuTable(new ApplicationMethods());
        menu.implementMenu(mt, new ApplicationMethods());

    }
}
