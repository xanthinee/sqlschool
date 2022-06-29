package sqltask.students;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import sqltask.connection.*;

public class StudentsTableDB {

    private final ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    String connectionFile = "data/connectioninfo";

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

    public List<Student> getStudents() throws SQLException {

        List<Student> students = new ArrayList<>();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement psStudents = connection.prepareStatement("select * from students");
            ResultSet rsStudents = psStudents.executeQuery();
            while (rsStudents.next()) {
                students.add(new Student(rsStudents.getInt("student_id"), rsStudents.getInt("group_id"),
                        rsStudents.getString("first_name").trim(), rsStudents.getString("second_name").trim()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}