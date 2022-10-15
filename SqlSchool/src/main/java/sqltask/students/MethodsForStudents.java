package sqltask.students;

import java.util.*;
import sqltask.helpers.*;


public class MethodsForStudents {

    static final int TOTAL_AMOUNT_OF_STUDENTS = 200;
    static final Set<Integer> usedNUMs = new HashSet<>();
    Random rd = new Random();
    public int generateUniqueNum(int leftBound, int rightBound) {
        int num = rd.nextInt(leftBound, rightBound);
        while (usedNUMs.contains(num)) {
            num = rd.nextInt(leftBound, rightBound);
        }
        usedNUMs.add(num);
        return num;
    }

    public List<Student> generateStudents() {

        CustomFileReader fileCon = new CustomFileReader();
        List<String> names = fileCon.readFile("data/names.txt").toList();
        List<String> surnames = fileCon.readFile("data/surnames.txt").toList();
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < TOTAL_AMOUNT_OF_STUDENTS; i++) {
            students.add(new Student(null, null,
                    names.get(rd.nextInt(0, names.size())),
                    surnames.get(rd.nextInt(0, surnames.size()))));
        }
        return students;
    }

    public String printStudentsTable(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENTS");

        for (Student student : students) {
            int idOfGroup = student.getGroupId();
            sj.add(String.format("%-6d", student.getStudentId()) + " | "
            + String.format("%-6d", idOfGroup) + " | "
            + String.format("%-9s", student.getName()) + " | "
            + String.format("%-12s", student.getSurname()));
        }
        return sj.toString();
    }

    public String printStudentsPartly(List<Student> students) {

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
