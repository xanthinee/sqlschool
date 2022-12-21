package sqltask.courses;

import sqltask.connection.DataConnection;
import sqltask.students.Student;
import sqltask.students.StudentMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    private final DataConnection ds;
    private static final String COURSES_TABLE = "courses";
    private static final String STUDENTS_COURSES_TABLE = "students_courses";
    private static final String STUDENTS_TABLE = "students";
    private final CourseMapper courseMapper = new CourseMapper();
    private final StudentMapper studentMapper = new StudentMapper();

    public CourseDAOImpl(DataConnection ds) {
        this.ds = ds;
    }


    @Override
    public void saveAll(List<Course> courses) {

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into " + COURSES_TABLE + " values (default,?,?)")) {
            for (Course course : courses) {
                courseMapper.mapToRow(ps, course);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Course course) {

        try (Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement("insert into " +  COURSES_TABLE + " values (default, ?,?)")) {
            courseMapper.mapToRow(ps, course);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("delete from " + COURSES_TABLE)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAll() {

        List<Course> courses = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select * " +
                     "FROM " + COURSES_TABLE)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(courseMapper.mapToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public List<Course> getCoursesOfStudent(int studentID) {

        List<Course> coursesOfStudent = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select c.course_id, c.course_name, c.course_description" +
                     " from " + COURSES_TABLE + " c inner join " + STUDENTS_COURSES_TABLE + " sc " +
                     "on c.course_id = sc.course_id where student_id = ?")) {
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                coursesOfStudent.add(courseMapper.mapToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesOfStudent;
    }

    @Override
    public List<Course> findAvailableCourses(int studentID) {

        List<Course> available = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(" select c.course_id, c.course_name, c.course_description" +
                " from " + COURSES_TABLE + " c " +
                "where c.course_id not in (select sc.course_id from " + STUDENTS_COURSES_TABLE + " sc where sc.student_id = ?) ")){
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                available.add(courseMapper.mapToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return available;
    }

    @Override
    public List<Student> getCourseMembers(String courseName) {

        List<Student> students = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select s.student_id, s.group_id, s.first_name, s.second_name \n" +
                     "from " +  STUDENTS_TABLE + " s " +
                     "inner join " + STUDENTS_COURSES_TABLE + " sc on sc.student_id = s.student_id " +
                     "inner join " + COURSES_TABLE + " c on sc.course_id = c.course_id " +
                     "where c.course_name = ?")) {
            ps.setString(1, courseName);
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
    public void unlinkCourse(int studentID, String courseToDelete) {

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(" delete from " + STUDENTS_COURSES_TABLE + " sc " +
                     "using " + COURSES_TABLE + " c " +
                     "where c.course_id = sc.course_id and " +
                     "sc.student_id = ? and c.course_name = ?")) {
            ps.setInt(1, studentID);
            ps.setString(2, courseToDelete);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNewCourse(int studentID, String courseName) {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(" insert into " + STUDENTS_COURSES_TABLE + " (student_id, course_id) " +
                     "select ?, course_id from " + COURSES_TABLE + " where course_name = ?")) {
            ps.setInt(1, studentID);
            ps.setString(2, courseName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Course getById(int id) {

        try (Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement(" select * from " + COURSES_TABLE + " where course_id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return courseMapper.mapToEntity(rs);
            }
            throw new IllegalStateException("No courses with such ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(int id) {

        try (Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement(" delete from " + COURSES_TABLE + " where course_id = ? ")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllFromStudentsCourses() {

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("delete from " + STUDENTS_COURSES_TABLE)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveStudentsCourses(Student student, List<Course> courses) {

            try (Connection con = ds.getConnection();
                 PreparedStatement ps = con.prepareStatement("insert into " + STUDENTS_COURSES_TABLE + " values (?,?)")) {
                for (Course course : courses) {
                    ps.setInt(1, student.getStudentId());
                    ps.setInt(2, course.getId());
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
