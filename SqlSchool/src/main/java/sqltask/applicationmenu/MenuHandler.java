package sqltask.applicationmenu;

import sqltask.connection.ConnectionProvider;
import sqltask.courses.Course;
import sqltask.courses.CoursesTableDB;
import sqltask.courses.MethodsForCourses;
import sqltask.groups.GroupsTableDB;
import sqltask.groups.MethodsForGroups;
import sqltask.students.StudentsTableDB;
import sqltask.studentscourses.MethodsForStudCourses;
import sqltask.studentscourses.StudentsCoursesTableDB;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.*;

@SuppressWarnings("java:S106")
public class MenuHandler {

    Scanner sc = new Scanner(System.in);
    ConnectionProvider conProvider = new ConnectionProvider();
    GroupsTableDB groupDB = new GroupsTableDB(conProvider);
    StudentsCoursesTableDB studentsCoursesDB = new StudentsCoursesTableDB(conProvider);
    StudentsTableDB studentsDB = new StudentsTableDB(conProvider);
    CoursesTableDB coursesDB = new CoursesTableDB(conProvider);
    MethodsForCourses coursesMethods = new MethodsForCourses();
    MethodsForStudCourses studCourseMethods = new MethodsForStudCourses();
    MethodsForGroups groupsMethods = new MethodsForGroups();
    Service service = new Service();

    public Object compareGroup() {
        System.out.println("Enter ID of Group:");
        int groupID = sc.nextInt();
        System.out.println(groupsMethods.printResultOfComparison(groupDB.compareGroups(groupID)));
        return groupDB.compareGroups(groupID);
    }

    public Object findStudentsByCourse() {
        System.out.println("ENTER name of COURSE bellow: ");
        String courseName = sc.next();
        System.out.println(studCourseMethods.printMembers(studentsCoursesDB.getCourseMembers(courseName)));
        return studentsCoursesDB.getCourseMembers(courseName);
    }

    public Void addStudent() {
        System.out.println("Enter NAME of student: ");
        String studentName = sc.next();
        System.out.println("Enter SURNAME of student");
        String studentSurname = sc.next();
        System.out.println("Enter ID of GROUP which new STUDENT will have bellow: ");
        int groupId = sc.nextInt();
        studentsDB.putNewStudent(studentName, studentSurname, groupId);
        return null;
    }

    public Void deleteStudent() {
        System.out.println("Enter ID of student: ");
        int studentID = sc.nextInt();
        studentsDB.deleteStudent(studentID);
        return null;
    }

    public Void setNewCourse() {
        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        List<Course> list = coursesDB.getCoursesOfStudent(studentID);
        System.out.println(coursesMethods.printCoursesOfStud(list));
        System.out.println(coursesMethods.infoToPrint(list, coursesDB.findAvailableCourses(studentID)));
        System.out.println("Enter NAME (Only 1 by attempt) of COURSE which you want to ADD: ");
        String courseName = sc.next();
        coursesDB.setNewCourse(studentID, courseName);
        return null;
    }

    public Void unlinkCourse() {
        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        System.out.println(coursesMethods.printCoursesOfStud(coursesDB.getCoursesOfStudent(studentID)));
        System.out.println("You can DELETE one of them - ENTER bellow it's NAME: ");
        String courseToDelete = sc.next();
        studentsCoursesDB.unlinkCourse(studentID, courseToDelete);
        return null;
    }
}
