package sqltask.applicationmenu.menufunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sqltask.students.Student;
import sqltask.students.StudentService;
import sqltask.applicationmenu.Menu;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.*;
@SuppressWarnings("java:S106")
@Component
public class DeleteStudentMenuItem implements Menu {

    private final StudentService service;
    private final InputStream inputStream;
    private final PrintStream outStream;

    @Autowired
    public DeleteStudentMenuItem (StudentService service) {
        this(service, System.in, System.out);
    }

    public DeleteStudentMenuItem (StudentService service, InputStream inputStream, PrintStream outStream) {
        this.service = service;
        this.inputStream = inputStream;
        this.outStream = outStream;
    }

    @Override
    public String getLabel() {
        return "Delete Student from DB";
    }

    @Override
    public void doAction() {
        List<Student> studentList = service.getAll();
        List<Integer> studentsIDs = new ArrayList<>();
        for (Student student : studentList) {
            studentsIDs.add(student.getStudentId());
        }
        Scanner sc = new Scanner(inputStream);
        outStream.println("Enter ID of student: ");
        try {
            int studentID = sc.nextInt();
            if (!studentsIDs.contains(studentID)) {
                throw new IllegalArgumentException("No student with such id");
            }
            service.deleteById(studentID);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
