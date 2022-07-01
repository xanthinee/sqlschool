package sqltask.students;

import java.sql.SQLException;
import java.util.*;
import sqltask.groups.GroupsTableDB;
import sqltask.helpers.*;

public class StudentsMethods {

    static final int TOTAL_AMOUNT_OF_STUDENTS = 200;
    static final Set<Integer> usedIDs = new HashSet<>();
    Random rd = new Random();
    public int generateUniqueNum(int leftBound, int rightBound) {
        int num = rd.nextInt(leftBound, rightBound);
        if (usedIDs.contains(num)) {
            num = rd.nextInt(leftBound, rightBound);
        }
        usedIDs.add(num);
        return num;
    }

    public List<Student> generateStudents() {

        customFileReader fileCon = new customFileReader();
        List<String> names = fileCon.readFile("data/names.txt").toList();
        List<String> surnames = fileCon.readFile("data/surnames.txt").toList();
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < TOTAL_AMOUNT_OF_STUDENTS; i++) {
            students.add(new Student(generateUniqueNum(1, 10000), null,
                    names.get(generateUniqueNum(0, names.size())),
                    surnames.get(generateUniqueNum(0, surnames.size()))));
        }
        return students;
    }

    public String printStudentsTable(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENTS");

        for (Student student : students) {
            int idOfGroup = student.getGroupId();
            sj.add(student.getStudentId() + " | "
            + String.format("%-6d", idOfGroup) + " | "
            + String.format("%-9s", student.getName()) + " | "
            + String.format("%-12s", student.getSurname()));
        }
        return sj.toString();
    }
}
