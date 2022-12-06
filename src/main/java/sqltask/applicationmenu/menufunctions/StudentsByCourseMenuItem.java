package sqltask.applicationmenu.menufunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sqltask.applicationmenu.*;
import sqltask.courses.Course;
import sqltask.courses.CourseService;
import sqltask.courses.CourseUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

@Component
@SuppressWarnings("java:S106")
public class StudentsByCourseMenuItem implements Menu {

    private final CourseService service;
    private final InputStream inputStream;
    private final PrintStream outStream;

    @Autowired
    public StudentsByCourseMenuItem (CourseService service) {
        this (service, System.in, System.out);
    }

    public StudentsByCourseMenuItem (CourseService service, InputStream inputStream, PrintStream outStream) {
        this.service = service;
        this.inputStream = inputStream;
        this.outStream = outStream;
    }

    @Override
    public String getLabel() {
        return "Get all students with chosen course";
    }

    @Override
    public void doAction() {

        outStream.println("List of all courses:");
        outStream.println(CourseUtils.printCourses(service.getAll()));
        Scanner sc = new Scanner(inputStream);
        outStream.println("ENTER name of COURSE bellow: ");
        String courseName = sc.next();
        outStream.println(CourseUtils.printMembers(service.getCourseMembers(courseName)));
    }
}
