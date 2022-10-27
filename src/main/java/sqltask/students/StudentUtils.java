package sqltask.students;

import java.util.*;

public class StudentUtils {

    public static String printStudentsTable(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENTS");

        for (Student student : students) {
            int idOfGroup = student.getGroupId();
            sj.add(String.format("%-5d", student.getStudentId()) + "|"
            + String.format("%-5d", idOfGroup) + "|"
            + String.format("%-10s", student.getName()) + "|"
            + String.format("%-12s", student.getSurname()).trim());
        }
        return sj.toString();
    }

    public static String printStudentsPartly(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Students which have desired course:");
        for (Student student : students) {
            sj.add(String.format("%-6d", student.getStudentId()) + " | "
                    + String.format("%-6d", student.getGroupId()) + " | "
                    + String.format("%-12s", student.getName()) + " | "
                    + String.format("%s", student.getSurname()));
        }
        return sj.toString();
    }
}
