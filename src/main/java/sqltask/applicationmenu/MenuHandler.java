package sqltask.applicationmenu;

import sqltask.connection.DataSource;
import sqltask.courses.Course;
import sqltask.courses.CourseDAOImpl;
import sqltask.courses.CourseUtil;
import sqltask.groups.GroupDAOImpl;
import sqltask.groups.GroupUtils;
import sqltask.students.Student;
import sqltask.students.StudentDAOImpl;

import java.util.Scanner;
import java.util.*;

@SuppressWarnings("java:S106")
public class MenuHandler {

    Scanner sc = new Scanner(System.in);
    DataSource conProvider = new DataSource();
    GroupDAOImpl groupDB = new GroupDAOImpl(conProvider, "groups");
    StudentDAOImpl studentsDB = new StudentDAOImpl(conProvider);
    CourseDAOImpl coursesDB = new CourseDAOImpl(conProvider, "courses", "students_courses");
    CourseUtil coursesMethods = new CourseUtil();
    GroupUtils groupsMethods = new GroupUtils();

    public Object compareGroup() {
        System.out.println("Enter ID of Group:");
        int groupID = sc.nextInt();
        System.out.println(groupsMethods.printResultOfComparison(groupDB.compareGroups(groupID)));
        return groupDB.compareGroups(groupID);
    }

    public Object findStudentsByCourse() {
        System.out.println("ENTER name of COURSE bellow: ");
        String courseName = sc.next();
        System.out.println(coursesMethods.printMembers(coursesDB.getCourseMembers(courseName)));
        return coursesDB.getCourseMembers(courseName);
    }

    public Void addStudent() {
        System.out.println("Enter NAME of student: ");
        String studentName = sc.next();
        System.out.println("Enter SURNAME of student");
        String studentSurname = sc.next();
        System.out.println("Enter ID of GROUP which new STUDENT will have bellow: ");
        int groupId = sc.nextInt();
        studentsDB.save(new Student(null, groupId, studentName, studentSurname));
        return null;
    }

    public Void deleteStudent() {
        System.out.println("Enter ID of student: ");
        int studentID = sc.nextInt();
        studentsDB.deleteById(studentID);
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
        coursesDB.unlinkCourse(studentID, courseToDelete);
        return null;
    }
}
