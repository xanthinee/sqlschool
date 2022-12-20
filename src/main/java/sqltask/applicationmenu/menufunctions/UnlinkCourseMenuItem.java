package sqltask.applicationmenu.menufunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sqltask.courses.Course;
import sqltask.courses.CourseService;
import sqltask.applicationmenu.*;
import sqltask.courses.CourseUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.*;

@SuppressWarnings("java:S106")
@Component
public class UnlinkCourseMenuItem implements Menu {

    private final CourseService service;
    private final InputStream inputStream;
    private final PrintStream outStream;

    @Autowired
    public UnlinkCourseMenuItem (CourseService service) {
        this (service, System.in, System.out);
    }

    public UnlinkCourseMenuItem (CourseService service, InputStream inputStream, PrintStream outStream) {
        this.service = service;
        this.inputStream = inputStream;
        this.outStream = outStream;
    }

    @Override
    public String getLabel() {
        return "Unlink course";
    }

    @Override
    public void doAction() {
        Scanner sc = new Scanner(inputStream);
        outStream.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        List<Course> courses = service.getCoursesOfStudent(studentID);
        List<String> courseNames = new ArrayList<>();
        for (Course course : courses) {
            courseNames.add(course.getName());
        }
        outStream.println(CourseUtils.printCoursesOfStud(courses));
        outStream.println("You can DELETE one of them - ENTER bellow it's NAME: ");
        try {
            String courseToDelete = sc.next();
            if (!courseNames.contains(courseToDelete)) {
                throw new IllegalArgumentException("Student doesn't have such course");
            }
            service.unlinkCourse(studentID, courseToDelete);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
