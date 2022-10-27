package sqltask.students;

import sqltask.connection.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("java:S106")
public class StudentDAOImpl implements StudentDAO {

    private static final String STUDENTS_TABLE = "students";
    private final StudentMapper studentMapper = new StudentMapper();
    private final DataSource ds;


    public StudentDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public void saveAll(List<Student> students) {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into public.students values (default,null,?,?)")){
            for (Student student : students) {
                studentMapper.mapToRow(ps, student);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("delete from students")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAll() {

        List<Student> students = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select * " +
                     " from students")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students.add(studentMapper.mapToEntity(rs));
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
             PreparedStatement putStudent = con.prepareStatement("insert into students values (default,null,?,?)")) {
            studentMapper.mapToRow(putStudent, student);
            putStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGroupIdByStudId(Student student, int groupID) {

        try (Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement("update public.students set group_id = ? where student_id = " +
                student.getStudentId())) {
            ps.setInt(1, groupID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getById(int id) {

        try (Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from " + STUDENTS_TABLE + " WHERE student_id = ? ")) {
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