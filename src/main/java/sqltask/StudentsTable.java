package sqltask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;

public class StudentsTable {

    ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    static final int TOTAL_AMOUNT_OF_STUDENTS = 200;

    private List<Integer> groupsIdList() throws SQLException {

        List<Integer> ids = new ArrayList<>();
        try (Connection connection = conInfo.getConnection("textdata/connectioninfo.txt")) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM public.groups");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        }
        return ids;
    }

    public List<Student> generateStudents() {

        Random rd = new Random();
        FileConverter fileCon = new FileConverter();
        List<String> names = fileCon.readFile("textdata/names.txt").toList();
        List<String> surnames = fileCon.readFile("textdata/surnames.txt").toList();
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < TOTAL_AMOUNT_OF_STUDENTS; i++) {
            students.add(new Student(rd.nextInt(1000, 10000), null,
                    names.get(rd.nextInt(0, names.size())), surnames.get(rd.nextInt(0, surnames.size()))));
        }
        return students;
    }

    public void setStudentsGroups() throws SQLException {

        Random rd = new Random();
        List<Student> students = generateStudents();
        List<Integer> iDs = groupsIdList();

        for (int id : iDs) {
            int groupMembers = rd.nextInt(0, 31);
            if (groupMembers >= 10) {
                for (int i = 0; i < groupMembers; i++) {
                    students.get(rd.nextInt(0, students.size())).setGroupId(id);
                }
            }
        }
        for(Student student : students) {
            System.out.println(student);
        }
    }
}