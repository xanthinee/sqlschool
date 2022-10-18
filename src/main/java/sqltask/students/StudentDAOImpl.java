package sqltask.students;

import sqltask.connection.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("java:S106")
public class StudentDAOImpl implements StudentDAO {

    private final String tableName;
    private final StudentMapper studentMapper = new StudentMapper();
    private final DataSource ds;


    public StudentDAOImpl(DataSource ds) {
        this.ds = ds;
        this.tableName = "students";
    }

    @Override
    public void putStudentsIntoTable(List<Student> students) {
        try (Connection con = ds.getConnection();
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

    @Override
    public void deleteAll() {

        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement("delete from students")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAll() {

        List<Student> students = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement psStudents = con.prepareStatement("select * " +
                     " from students")) {
            ResultSet rsStudents = psStudents.executeQuery();
            while (rsStudents.next()) {
                students.add(studentMapper.mapToEntity(rsStudents));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void deleteById(int studentID) {

        try (Connection con = ds.getConnection();
             PreparedStatement deleteFromStudents = con.prepareStatement("delete from students where student_id = ?")) {
            deleteFromStudents.setInt(1, studentID);
            deleteFromStudents.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Student student) {

        try (Connection con = ds.getConnection();
             PreparedStatement putStudent = con.prepareStatement("insert into students values (default,?,?,?)")) {
            putStudent.setInt(1, student.getGroupId());
            putStudent.setString(2, student.getName());
            putStudent.setString(3, student.getSurname());
            putStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getById(int id) {

        try (Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from " + tableName + " WHERE student_id = ? ")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return studentMapper.mapToEntity(rs);
            }
            throw new IllegalStateException("No data found for id " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}