package sqltask.students;

import java.util.*;

public class StudentUtils {

    private static final int maxLengthNameColumn = 12;
    private static final int maxLengthSurnameColumn = 15;
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

            if(student.getName().length() > maxLengthNameColumn) {
                student.setName(trimToColumn(student.getName(), maxLengthNameColumn));
            }

            if(student.getSurname().length() > maxLengthSurnameColumn) {
                student.setSurname(trimToColumn(student.getSurname(), maxLengthSurnameColumn));
            }

            int idOfGroup = student.getGroupId();
            sj.add(String.format("%-5d", student.getStudentId()) + "|"
                    + String.format("%-5d", idOfGroup) + "|"
                    + String.format("%-" + maxLengthNameColumn + "s", student.getName()) + "|"
                    + String.format("%-" + maxLengthSurnameColumn + "s", student.getSurname()));
        }
        return sj.toString();
    }

    public static String printStudentsPartly(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Students which have desired course:");
        for (Student student : students) {

            if(student.getName().length() > maxLengthNameColumn) {
                student.setName(trimToColumn(student.getName(), maxLengthNameColumn));
            }

            if(student.getSurname().length() > maxLengthSurnameColumn) {
                student.setSurname(trimToColumn(student.getSurname(), maxLengthSurnameColumn));
            }

            sj.add(String.format("%-6d", student.getStudentId()) + "|"
                    + String.format("%-6d", student.getGroupId()) + "|"
                    + String.format("%-" + maxLengthNameColumn + "s", student.getName()) + "|"
                    + String.format("%-" + maxLengthSurnameColumn + "s", student.getSurname()));
        }
        return sj.toString();
    }
}
