package sqltask.courses;

import sqltask.connection.DataSource;
import sqltask.students.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CourseDAOImpl implements CourseDAO {

    private final DataSource ds;
    private final String tableName;
    private final String manyToManyTable;
    CourseMapper courseMapper = new CourseMapper();
    Random rd = new Random();
    private static final String COURSE_ID = "course_id";
    private static final String COURSE_NAME = "course_name";
    private static final String COURSE_DESCRIPTION = "course_description";
    private static final String STUDENT_ID = "student_id";
    private static final String STUDENT_NAME = "first_name";
    private static final String STUDENT_SURNAME = "second_name";
    private static final String GROUP_ID = "group_id";

    public CourseDAOImpl(DataSource ds, String tableName, String manyToManyTable) {
        this.ds = ds;
        this.tableName = tableName;
        this.manyToManyTable = manyToManyTable;
    }


    @Override
    public void putCoursesInTable(List<Course> courses) {

        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement("insert into " + tableName + " values (default,?,?)")) {
            for (Course course : courses) {
                st.setString(1, course.getName());
                st.setString(2, course.getDescription());
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
             PreparedStatement st = con.prepareStatement("delete from " + tableName)) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAll() {

        List<Course> courses = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("select * " +
                     "FROM " + tableName)){
            ResultSet coursesRS = preparedStatement.executeQuery();
            while (coursesRS.next()) {
                courses.add(courseMapper.mapToEntity(coursesRS));
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
             PreparedStatement getCoursesOfStud = con.prepareStatement("select c.course_id, c.course_name, c.course_description" +
                     " from courses c inner join students_courses sc " +
                     "on c.course_id = sc.course_id where student_id = ?")) {
            getCoursesOfStud.setInt(1, studentID);
            ResultSet coursesOfStud = getCoursesOfStud.executeQuery();
            while (coursesOfStud.next()) {
                coursesOfStudent.add(new Course(coursesOfStud.getInt(COURSE_ID),coursesOfStud.getString(COURSE_NAME),
                        coursesOfStud.getString(COURSE_DESCRIPTION)));
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
             PreparedStatement avbCourses = con.prepareStatement(" select c.course_id, c.course_name, c.course_description" +
                " from courses c " +
                "where c.course_id not in (select sc.course_id from students_courses sc where sc.student_id = ?) ")){
            avbCourses.setInt(1, studentID);
            ResultSet avbCoursesRs = avbCourses.executeQuery();
            while (avbCoursesRs.next()) {
                available.add(new Course(avbCoursesRs.getInt(COURSE_ID), avbCoursesRs.getString(COURSE_NAME).trim(),
                        avbCoursesRs.getString(COURSE_DESCRIPTION).trim()));
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
             PreparedStatement st = con.prepareStatement("select s.student_id, s.group_id, s.first_name, s.second_name \n" +
                     "from students s " +
                     "inner join students_courses sc on sc.student_id = s.student_id " +
                     "inner join courses c on sc.course_id = c.course_id " +
                     "where c.course_name = ?")) {
            st.setString(1, courseName);
            ResultSet studentsRS = st.executeQuery();
            while (studentsRS.next()) {
                students.add(new Student(studentsRS.getInt(STUDENT_ID), studentsRS.getInt(GROUP_ID),
                        studentsRS.getString(STUDENT_NAME), studentsRS.getString(STUDENT_SURNAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void unlinkCourse(int studentID, String courseToDelete) {

        try (Connection con = ds.getConnection();
             PreparedStatement unlinkCourse = con.prepareStatement(" delete from " + manyToManyTable + " sc " +
                     "using courses c " +
                     "where c.course_id = sc.course_id and " +
                     "sc.student_id = ? and c.course_name = ?")) {
            unlinkCourse.setInt(1, studentID);
            unlinkCourse.setString(2, courseToDelete);
            unlinkCourse.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNewCourse(int studentID, String courseName) {
        try (Connection con = ds.getConnection();
             PreparedStatement addCourseToStudent = con.prepareStatement(" insert into " + manyToManyTable + " (student_id, course_id) " +
                     "select ?, course_id from courses where course_name = ?")) {
            addCourseToStudent.setInt(1, studentID);
            addCourseToStudent.setString(2, courseName);
            addCourseToStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Course getById(int id) {

        try (Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement(" select * from " + tableName + " where course_id = ?")) {
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
        PreparedStatement ps = con.prepareStatement(" delete from " + tableName + " where course_id = ? ")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllFromStudentsCourses() {

        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement("delete from students_courses")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
