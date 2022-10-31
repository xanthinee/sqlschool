package sqltask.students;

import java.util.*;

public class StudentUtils {

    private static final int MAX_LENGTH_NAME_COLUMN = 12;
    private static final int MAX_LENGTH_SURNAME_COLUMN = 15;
    private static final String TRIM_MARKER = "...";


    private static String trimToColumn(String value, int columnSize) {
        int meaningfulLength = columnSize - TRIM_MARKER.length();
        if (value.length() > columnSize) {
            return value.substring(0, meaningfulLength) + TRIM_MARKER;
        }
        return value;
    }

    public static String printStudentsTable(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENTS");

        for (Student student : students) {

            int idOfGroup = student.getGroupId();
            sj.add(String.format("%-5d", student.getStudentId()) + "|"
                    + String.format("%-5d", idOfGroup) + "|"
                    + String.format("%-" + MAX_LENGTH_NAME_COLUMN + "s", trimToColumn(student.getName(),
                    MAX_LENGTH_NAME_COLUMN)) + "|"
                    + String.format("%-" + MAX_LENGTH_SURNAME_COLUMN + "s",
                    trimToColumn(student.getSurname(),
                    MAX_LENGTH_SURNAME_COLUMN)));
        }
        return sj.toString();
    }

    public static String printStudentsPartly(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Students which have desired course:");
        for (Student student : students) {

            sj.add(String.format("%-6d", student.getStudentId()) + "|"
                    + String.format("%-6d", student.getGroupId()) + "|"
                    + String.format("%-" + MAX_LENGTH_NAME_COLUMN + "s", trimToColumn(student.getName(),
                    MAX_LENGTH_NAME_COLUMN)) + "|"
                    + String.format("%-" + MAX_LENGTH_SURNAME_COLUMN + "s", trimToColumn(student.getSurname(),
                    MAX_LENGTH_SURNAME_COLUMN)));
        }
        return sj.toString();
    }
}
