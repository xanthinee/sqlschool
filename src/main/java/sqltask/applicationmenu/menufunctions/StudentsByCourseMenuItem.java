package sqltask.applicationmenu.menufunctions;

import sqltask.applicationmenu.*;
import sqltask.courses.CourseService;
import sqltask.courses.CourseUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class StudentsByCourseMenuItem implements Menu {

    private final CourseService service;
    private final InputStream inputStream;
    private final PrintStream outStream;

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
        return "Get all students having exact course";
    }

    @Override
    public void doAction() {
        Scanner sc = new Scanner(inputStream);
        outStream.println("ENTER name of COURSE bellow: ");
        String courseName = sc.next();
        outStream.println(CourseUtils.printMembers(service.getCourseMembers(courseName)));
    }
}
