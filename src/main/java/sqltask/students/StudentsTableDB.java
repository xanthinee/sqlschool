package sqltask.students;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import sqltask.connection.*;

@SuppressWarnings("java:S106")
public class StudentsTableDB {

    private final ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    private static final String CONNENCTION_FILE = "data/connectioninfo";

    public void putStudentsIntoTable(List<Student> students) throws SQLException {
        try (Connection connection = conInfo.getConnection(CONNENCTION_FILE);
             PreparedStatement st = connection.prepareStatement("insert into public.students values (?,?,?,?)")){
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

        try (Connection connection = conInfo.getConnection(CONNENCTION_FILE);
             PreparedStatement st = connection.prepareStatement("delete from students")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getStudents() throws SQLException {

        List<Student> students = new ArrayList<>();
        try (Connection connection = conInfo.getConnection(CONNENCTION_FILE);
             PreparedStatement psStudents = connection.prepareStatement("select * from students")) {
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