package sqltask.application.methods.coursemembers;

import java.util.*;
import sqltask.students.Student;

public class MethodsForStudByCourse {

    public String printMembers(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENTS which have this COURSE: ");
        for (Student student : students) {
            sj.add(String.format("%-5d|", student.getStudentId())
            + String.format("%-5d|", student.getGroupId())
            + String.format("%-10s|", student.getName())
            + String.format("%1s", student.getSurname()));
        }
        return "abc";
    }
}
