package sqltask.applicationmenu.menufunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sqltask.students.StudentService;
import sqltask.applicationmenu.Menu;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

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
        Scanner sc = new Scanner(inputStream);
        outStream.println("Enter ID of student: ");
        int studentID = sc.nextInt();
        service.deleteById(studentID);
    }
}
