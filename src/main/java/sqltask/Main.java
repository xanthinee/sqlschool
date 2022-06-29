package sqltask;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;
import sqltask.helpers.*;
import sqltask.groups.*;
import sqltask.students.*;
import sqltask.courses.*;
import sqltask.applicationmethods.*;

public class Main {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

//        SQLScriptRunner sqlS = new SQLScriptRunner();
//        try {
//            sqlS.executeScriptUsingScriptRunner("/Users/xanthine/IdeaProjects/SqlSchool/src/main/resources/sqldata/tables_creation.sql");
//        } catch (IOException | SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }


//// create table of groups
//        GroupsMethods groupsTable = new GroupsMethods();
//        GroupsTableDB groupsDB = new GroupsTableDB();
//        groupsDB.putGroupIntoTable();
//        List<Group> groups = groupsDB.getGroupsFromTable();
//        System.out.println(groupsTable.printGroupsTable(groups));
//        System.out.println("---------------------------------------------");


//// put courses in table
//        CoursesTable coursesT = new CoursesTable();
//        List<Course> courses = coursesT.makeCoursesList("data/descriptions");
//        coursesT.putCoursesInTable(courses);
//        System.out.println(coursesT.printCoursesTable());


//// put students into table
//        StudentsMethods st = new StudentsMethods();
//        List<Student> students = st.finishStudentsCreation();
//        StudentsTableDB studentsDB = new StudentsTableDB();
//        studentsDB.putStudentsIntoTable(students);
//        List<Student> studentsFromDB = studentsDB.getStudents();
//        System.out.println(st.printStudentsTable(studentsFromDB));

//        // put students_courses table
//        StudentsCoursesTable stCoTab = new StudentsCoursesTable();
//        stCoTab.createStdCrsTable();
//        System.out.println(stCoTab.printStudCourseTable());

        ApplicationMethods appMethods = new ApplicationMethods();
        ApplicationMenu menu = new ApplicationMenu();
        List<MenuTable> mt = menu.getMenuTable(new ApplicationMethods());
        menu.implementMenu(mt, new ApplicationMethods());

    }
}
