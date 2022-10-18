package sqltask;

import sqltask.applicationmenu.MenuGroup;
import sqltask.applicationmenu.menufunctions.*;
import sqltask.connection.DataSource;
import sqltask.courses.CourseService;
import sqltask.courses.CoursesTableDB;
import sqltask.groups.GroupService;
import sqltask.groups.GroupsTableDB;
import sqltask.students.MethodsForStudents;
import sqltask.students.Student;
import sqltask.students.StudentService;
import sqltask.students.StudentsTableDB;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        DataSource dataSource = new DataSource();

        StudentsTableDB studentDAO = new StudentsTableDB(dataSource);
        GroupsTableDB groupDAO = new GroupsTableDB(dataSource, "groups");
        StudentService studentService = new StudentService(studentDAO);
        GroupService groupService = new GroupService(groupDAO);
        CoursesTableDB courseDAO = new CoursesTableDB(dataSource, "courses", "students_courses");
        CourseService courseService = new CourseService(courseDAO);

//        MenuGroup menuGroup = new MenuGroup("sql app");
//        menuGroup.addItem(new AddStudentMenuItem(studentService));
//        menuGroup.addItem(new DeleteStudentMenuItem(studentService));
//        menuGroup.addItem(new GroupsByStudentCountMenuItem(groupService));
//        menuGroup.addItem(new SetCourseMenuItem(courseService));
//        menuGroup.addItem(new StudentsByCourseMenuItem(courseService));
//        menuGroup.addItem(new UnlinkCourseMenuItem(courseService));
//        menuGroup.doAction();

        MethodsForStudents studMethods = new MethodsForStudents();
        List<Student> students = studentService.setGroupsToStudents();
        System.out.println("_____________________________________________");
        students.forEach(student -> System.out.println(student.toString()));

    }
}
