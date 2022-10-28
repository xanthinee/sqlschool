package sqltask.students;

import java.util.*;

public class StudentUtils {

    public static String printStudentsTable(List<Student> students) {

        int maxLengthNameColumn = 12;
        int maxLengthSurnameColumn = 15;

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENTS");

        for (Student student : students) {

            if(student.getName().length() > maxLengthNameColumn) {
                student.setName(student.getName().substring(0, maxLengthNameColumn - 3) + "...");
            }

            if(student.getSurname().length() > maxLengthSurnameColumn) {
                student.setSurname(student.getSurname().substring(0, maxLengthSurnameColumn - 3) + "...");
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

        int maxLengthNameColumn = 12;
        int maxLengthSurnameColumn = 15;

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Students which have desired course:");
        for (Student student : students) {

            if(student.getName().length() > maxLengthNameColumn) {
                student.setName(student.getName().substring(0, maxLengthNameColumn - 3) + "...");
            }

            if(student.getSurname().length() > maxLengthSurnameColumn) {
                student.setSurname(student.getSurname().substring(0, maxLengthSurnameColumn - 3) + "...");
            }

            sj.add(String.format("%-6d", student.getStudentId()) + "|"
                    + String.format("%-6d", student.getGroupId()) + "|"
                    + String.format("%-" + maxLengthNameColumn + "s", student.getName()) + "|"
                    + String.format("%-" + maxLengthSurnameColumn + "s", student.getSurname()));
        }
        return sj.toString();
    }
}
