package sqltask.students;

import sqltask.connection.ConnectionProvider;
import sqltask.groups.GroupsTableDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("java:S106")
public class StudentsTableDB {

    ConnectionProvider conProvider;
    Random rd = new Random();

    public StudentsTableDB(ConnectionProvider conProvider) {
        this.conProvider = conProvider;
    }


    public List<Student> finishStudentsCreation() throws SQLException {

        MethodsForStudents studMethods = new MethodsForStudents();
        List<Student> students = studMethods.generateStudents();
        GroupsTableDB groupsTab = new GroupsTableDB(conProvider);
        List<Integer> iDs = groupsTab.groupsIdList();
        for (int id : iDs) {
            int groupMembers = rd.nextInt(0, 31);
            if (groupMembers >= 10) {
                for (int i = 0; i < groupMembers; i++) {
                    students.get(studMethods.generateUniqueNum(0, students.size())).setGroupId(id);
                }
            }
        }
        return students;
    }
    public void putStudentsIntoTable(List<Student> students) throws SQLException {
        try (Connection con = conProvider.getConnection();
                PreparedStatement st = con.prepareStatement("insert into public.students values (default,?,?,?)")){
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentsFromTable() throws SQLException {

        try (Connection con = conProvider.getConnection();
             PreparedStatement st = con.prepareStatement("delete from students")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getStudents() throws SQLException {

        List<Student> students = new ArrayList<>();
        try {
            Connection con = conProvider.getConnection();
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

    public String deleteStudent(int studentID) {

        try (Connection con = conProvider.getConnection();
             PreparedStatement deleteFromStudents = con.prepareStatement("delete from students where student_id = ?")) {
            deleteFromStudents.setInt(1, studentID);
            deleteFromStudents.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "STUDENT WAS DELETED";
    }

    public String putNewStudent(String studentName, String studentSurname, int groupId) {

        try (Connection con = conProvider.getConnection();
             PreparedStatement putStudent = con.prepareStatement("insert into students values (default,?,?,?)")) {
            putStudent.setInt(1,groupId);
            putStudent.setString(2, studentName);
            putStudent.setString(3, studentSurname);
            putStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "NEW STUDENT WAS ADDED";
    }
}