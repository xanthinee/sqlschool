package sqltask;

import sqltask.applicationmenu.*;
import sqltask.applicationmenu.menufunctions.*;

import sqltask.connection.DataSource;
import sqltask.courses.CourseService;
import sqltask.courses.CourseDAOImpl;
import sqltask.groups.GroupService;
import sqltask.groups.GroupDAOImpl;
import sqltask.students.StudentUtils;
import sqltask.students.Student;
import sqltask.students.StudentService;
import sqltask.students.StudentDAOImpl;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        DataSource dataSource = new DataSource();

        StudentDAOImpl studentDAO = new StudentDAOImpl(dataSource);
        GroupDAOImpl groupDAO = new GroupDAOImpl(dataSource, "groups");
        StudentService studentService = new StudentService(studentDAO);
        GroupService groupService = new GroupService(groupDAO);
        CourseDAOImpl courseDAO = new CourseDAOImpl(dataSource, "courses", "students_courses");
        CourseService courseService = new CourseService(courseDAO);

        MenuGroup menuGroup = new MenuGroup("sql app");
        menuGroup.addItem(new AddStudentMenuItem(studentService));
        menuGroup.addItem(new DeleteStudentMenuItem(studentService));
        menuGroup.addItem(new GroupsByStudentCountMenuItem(groupService));
        menuGroup.addItem(new SetCourseMenuItem(courseService));
        menuGroup.addItem(new StudentsByCourseMenuItem(courseService));
        menuGroup.addItem(new UnlinkCourseMenuItem(courseService));
        menuGroup.doAction();


//        List<Student> students = studentService.setGroupsToStudents();
//        System.out.println("_____________________________________________");
//        students.forEach(student -> System.out.println(student.toString()));

    }
}
