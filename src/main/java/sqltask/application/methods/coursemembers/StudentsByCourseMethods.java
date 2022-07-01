package sqltask.application.methods.coursemembers;
import java.util.*;
import sqltask.students.*;

public class StudentsByCourseMethods {

    public String printStudents(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Students which have desired course:");
        for (Student student : students) {
            sj.add(String.format("%-6d", student.getStudentId()) + " | "
            + String.format("%-6d", student.getGroupId()) + " | "
            + String.format("%s", student.getName().trim()) + " "
            + String.format("%-13s", student.getSurname().trim()));
        }
        return sj.toString();
    }
}
