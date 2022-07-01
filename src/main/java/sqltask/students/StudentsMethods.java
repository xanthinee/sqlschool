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

        StringJoiner sj = new StringJoiner("");
        sj.add("STUDENTS");
        sj.add(System.lineSeparator());
        for (Student student : students) {
            sj.add(student.getStudentId() + " | ");
            int idOfGroup = student.getGroupId();
            sj.add(String.format("%-6d", idOfGroup) + " | ");
            sj.add(String.format("%-9s", student.getName()) + " | ");
            sj.add(String.format("%-12s", student.getSurname()));
            sj.add(System.lineSeparator());
        }
        return sj.toString();
    }
}
