package sqltask.applicationmenu.menufunctions;

import sqltask.courses.CourseService;
import sqltask.courses.CoursesTableDB;
import sqltask.applicationmenu.*;
import sqltask.courses.MethodsForCourses;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class UnlinkCourseMenuItem implements Menu {

    private final CourseService service;
    private final InputStream inputStream;
    private final PrintStream outStream;

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
        MethodsForCourses methods = new MethodsForCourses();
        outStream.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        outStream.println(methods.printCoursesOfStud(service.getCoursesOfStudent(studentID)));
        outStream.println("You can DELETE one of them - ENTER bellow it's NAME: ");
        String courseToDelete = sc.next();
        service.unlinkCourse(studentID, courseToDelete);
    }
}
