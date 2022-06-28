package sqltask;

import java.sql.*;
import java.util.*;

public class StudentsTable {

    ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    static final int TOTAL_AMOUNT_OF_STUDENTS = 200;
    static final Set<Integer> usedIDs = new HashSet<>();
    Random rd = new Random();
    String connectionFile = "data/connectioninfo";

    public List<Integer> groupsIdList() throws SQLException {

        List<Integer> ids = new ArrayList<>();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM public.groups");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        }
        return ids;
    }

    private int generateUniqueNum(int leftBound, int rightBound) {
        int num = rd.nextInt(leftBound, rightBound);
        if (usedIDs.contains(num)) {
            num = rd.nextInt(leftBound, rightBound);
        }
        usedIDs.add(num);
        return num;
    }

    public List<Student> generateStudents() {

        FileConverter fileCon = new FileConverter();
        List<String> names = fileCon.readFile("data/names").toList();
        List<String> surnames = fileCon.readFile("data/surnames").toList();
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < TOTAL_AMOUNT_OF_STUDENTS; i++) {
            students.add(new Student(generateUniqueNum(1000, 10000), null,
                    names.get(generateUniqueNum(0, names.size())),
                    surnames.get(generateUniqueNum(0, surnames.size()))));
        }
        return students;
    }
    public List<Student> finishStudentsCreation() throws SQLException {

        List<Student> students = generateStudents();
        List<Integer> iDs = groupsIdList();
        for (int id : iDs) {
            int groupMembers = generateUniqueNum(0, 31);
            if (groupMembers >= 10) {
                for (int i = 0; i < groupMembers; i++) {
                    students.get(generateUniqueNum(0, students.size())).setGroupId(id);
                }
            }
        }
        return students;
    }

    public void putStudentsIntoTable(List<Student> students) throws SQLException {
        System.out.println("1");
        try (Connection connection = conInfo.getConnection(connectionFile)){
            String query = "insert into public.students values (?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(query);
            for (Student student : students) {
                st.setInt(1, student.getStudentId());
                if (student.getGroupId() == null) {
                    st.setNull(2, Types.NULL);
                } else {
                    st.setInt(2, student.getGroupId());
                }
                st.setString(3, student.getName());
                st.setString(4, student.getSurname());
                st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentsFromTable() throws SQLException {

        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement st = connection.prepareStatement("delete from students");
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getStudentsFromTable() throws SQLException {

        try (Connection connection = conInfo.getConnection(connectionFile)){
            PreparedStatement st = connection.prepareStatement("select * from students");
            return st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("ResultSet wasn't created");
    }

    public String printStudentsTable() throws SQLException {

        ResultSet rs = getStudentsFromTable();
        StringJoiner sj = new StringJoiner("");
        sj.add("STUDENTS");
        sj.add(System.lineSeparator());
        while (rs.next()) {
            sj.add(rs.getInt("student_id") + " | ");
            sj.add(String.format("%-6d", rs.getInt("group_id")) + " | ");
            sj.add(String.format("%-9s", rs.getString("first_name").trim()) + " | ");
            sj.add(String.format("%-12s", rs.getString("second_name").trim()) + " | ");
            if (!rs.isLast()) {
                sj.add(System.lineSeparator());
            }
        }
        return sj.toString();
    }
}