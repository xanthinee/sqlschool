package sqltask.applicationmenu.menufunctions;

import sqltask.applicationmenu.Menu;
import sqltask.students.Student;
import sqltask.students.StudentService;

import java.io.InputStream;
import java.io.*;
import java.util.Scanner;

@SuppressWarnings("java:S106")
public class AddStudentMenuItem implements Menu {

    private final StudentService service;
    private final InputStream inputStream;
    private final PrintStream outputStream;

    public AddStudentMenuItem (StudentService service) {
        this(service, System.in, System.out);
    }

    public AddStudentMenuItem (StudentService service, InputStream inputStream, PrintStream outputStream) {
        this.service = service;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public String getLabel() {
        return "Add new student to DB";
    }

    @Override
    public void doAction() {
        outputStream.println("Enter NAME of student: ");
        Scanner sc = new Scanner(inputStream);
        String studentName = sc.next();
        outputStream.println("Enter SURNAME of student");
        String studentSurname = sc.next();
        outputStream.println("Enter ID of GROUP which new STUDENT will have bellow: ");
        outputStream.println("If new STUDENT has no GROUP then type 'null'");
        outputStream.println("If you want randomly assign GROUP to STUDENT type 'random'");
        String groupId = sc.next();
        if (groupId.equals("null")) {
            service.save(new Student(null, null, studentName, studentSurname));
        } else {
            service.save(new Student(null, Integer.valueOf(groupId), studentName, studentSurname));
        }
    }
}
