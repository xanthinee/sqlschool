package sqltask.applicationmenu.menufunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sqltask.courses.Course;
import sqltask.courses.CourseService;
import sqltask.applicationmenu.*;
import sqltask.courses.CourseUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

@Component
@SuppressWarnings("java:S106")
public class SetCourseMenuItem implements Menu {

    private final CourseService service;
    private final InputStream inputStream;
    private final PrintStream outStream;

    @Autowired
    public SetCourseMenuItem (CourseService service) {
        this (service, System.in, System.out);
    }

    public SetCourseMenuItem (CourseService service, InputStream inputStream, PrintStream outStream) {
        this.service = service;
        this.inputStream = inputStream;
        this.outStream = outStream;
    }

    @Override
    public String getLabel() {
        return "Set new course to Student";
    }

    @Override
    public void doAction() {
        Scanner sc = new Scanner(inputStream);
        outStream.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        List<Course> list = service.getCoursesOfStudent(studentID);
        outStream.println(CourseUtils.printCoursesOfStud(list));
//        outStream.println(CourseUtils.infoToPrint(list, service.findAvailableCourses(studentID)));
        outStream.println("Enter NAME (Only 1 by attempt) of COURSE which you want to ADD: ");
        String courseName = sc.next();
        service.setNewCourse(studentID, courseName);
    }
}
