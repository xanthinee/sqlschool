package sqltask.application.methods.coursemembers;
import java.util.*;
import sqltask.students.*;

public class StudentsByCourseMethods {

    public String printStudents(List<Student> students) {

        StringJoiner sj = new StringJoiner("");
        sj.add("Students which have desired course:");
        sj.add(System.lineSeparator());
        for (Student student : students) {
            sj.add(String.format("%-6d", student.getStudentId()) + " | ");
            sj.add(String.format("%-6d", student.getGroupId()) + " | ");
            sj.add(String.format("%s", student.getName().trim()) + " ");
            sj.add(String.format("%-13s", student.getSurname().trim()));
            sj.add(System.lineSeparator());
            System.out.println(sj);
        }
        return sj.toString();
    }
}
