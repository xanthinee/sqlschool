package sqltask.students;

import sqltask.groups.GroupsTableDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("java:S106")
public class StudentsTableDB {


    public List<Student> finishStudentsCreation(Connection con) throws SQLException {

        MethodsForStudents studMethods = new MethodsForStudents();
        List<Student> students = studMethods.generateStudents();
        GroupsTableDB groupsTab = new GroupsTableDB();
        List<Integer> iDs = groupsTab.groupsIdList(con);
        for (int id : iDs) {
            int groupMembers = studMethods.generateUniqueNum(0, 31);
            if (groupMembers >= 10) {
                for (int i = 0; i < groupMembers; i++) {
                    students.get(studMethods.generateUniqueNum(0, students.size())).setGroupId(id);
                }
            }
        }
        return students;
    }
    public void putStudentsIntoTable(Connection con, List<Student> students) throws SQLException {
        try (PreparedStatement st = con.prepareStatement("insert into public.students values (default,?,?,?)")){
            con.setAutoCommit(false);
            for (Student student : students) {
                if (student.getGroupId() == null) {
                    st.setNull(1, Types.NULL);
                } else {
                    st.setInt(1, student.getGroupId());
                }
                st.setString(2, student.getName());
                st.setString(3, student.getSurname());
                st.addBatch();
            }
            st.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentsFromTable(Connection con) throws SQLException {

        try (Connection connection = con;
             PreparedStatement st = connection.prepareStatement("delete from students")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getStudents(Connection con) throws SQLException {

        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement psStudents = con.prepareStatement("select student_id, group_id, first_name, second_name" +
                    " from students");
            ResultSet rsStudents = psStudents.executeQuery();
            while (rsStudents.next()) {
                students.add(new Student(rsStudents.getInt("student_id"), rsStudents.getInt("group_id"),
                        rsStudents.getString("first_name"), rsStudents.getString("second_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}