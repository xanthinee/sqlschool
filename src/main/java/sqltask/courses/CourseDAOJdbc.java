package sqltask.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sqltask.students.Student;
import sqltask.students.StudentRowMapper;

import java.sql.PreparedStatement;
import java.util.*;


@SuppressWarnings("java:S1874")
@Repository
public class CourseDAOJdbc implements CourseDAO {

    private static final String COURSE_TABLE = "courses";
    private static final String STUDENTS_COURSES_TABLE = "students_courses";
    private static final String STUDENT_TABLE = "students";
    private static final String INSERT_INTO_STUDENTS_COURSES = "insert into " + STUDENTS_COURSES_TABLE + " values(default,?,?)";
    private static final String GET_BY_ID = "select * from " + COURSE_TABLE + " where course_id = ?";
    private static final String DELETE_ALL = "delete from " + COURSE_TABLE;
    private static final String DELETE_ALL_STUDENTS_COURSES = "delete from " + STUDENTS_COURSES_TABLE;
    private static final String DELETE_BY_ID = "DELETE FROM " + COURSE_TABLE + " WHERE course_id = ?";
    private static final String SAVE_COURSE = "insert into " + COURSE_TABLE + " values(default,?,?)";
    private static final String GET_ALL = "select * from " + COURSE_TABLE;
    private static final String SET_NEW_COURSE = " insert into " + STUDENTS_COURSES_TABLE + " (student_id, course_id) " +
            "select ?, course_id from " + COURSE_TABLE + " where course_name = ?";
    private static final String UNLINK_COURSE = " delete from " + STUDENTS_COURSES_TABLE + " sc " +
            "using " + COURSE_TABLE + " c " +
            "where c.course_id = sc.course_id and " +
            "sc.student_id = ? and c.course_name = ?";

    private static final String GET_COURSE_MEMBERS = " select s.student_id, s.group_id, s.first_name, s.second_name " +
            "from " +  STUDENT_TABLE + " s " +
            "inner join " + STUDENTS_COURSES_TABLE + " sc on sc.student_id = s.student_id " +
            "inner join " + COURSE_TABLE + " c on sc.course_id = c.course_id " +
            "where c.course_name = ? ";

    private static final String FIND_AVAILABLE_COURSES = " select c.course_id, c.course_name, c.course_description" +
            " from " + COURSE_TABLE + " c " +
            "where c.course_id not in (select sc.course_id from " + STUDENTS_COURSES_TABLE + " sc where sc.student_id = ?) ";

    private static final String GET_COURSES_OF_STUDENT = "select c.course_id, c.course_name, c.course_description" +
            " from " + COURSE_TABLE + " c inner join " + STUDENTS_COURSES_TABLE + " sc " +
            "on c.course_id = sc.course_id where student_id = ?" ;
    private final JdbcTemplate jdbcTemplate;
    private final CourseRowMapper courseRowMapper = new CourseRowMapper();
    private final StudentRowMapper studentRowMapper = new StudentRowMapper();

    @Autowired
    public CourseDAOJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Course course) {
        jdbcTemplate.update(SAVE_COURSE, course.getName(), course.getDescription());
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(GET_ALL, courseRowMapper);
    }

    @Override
    public Course getById(int id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, courseRowMapper, id);
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.execute(DELETE_ALL);
    }

    @Override
    public void saveAll(List<Course> courses) {
        jdbcTemplate.batchUpdate(SAVE_COURSE, courses, courses.size(),
                (PreparedStatement ps, Course course ) -> {
            ps.setString(1, course.getName());
            ps.setString(2, course.getDescription());
                });
    }

    @Override
    public void saveStudentsCourses(Student student, List<Course> courses) {
        jdbcTemplate.batchUpdate(INSERT_INTO_STUDENTS_COURSES, courses, courses.size(),
                (PreparedStatement ps, Course course) -> {
            ps.setInt(1, student.getStudentId());
            ps.setInt(2, course.getId());
                });
    }

    @Override
    public void deleteAllFromStudentsCourses() {
        jdbcTemplate.execute(DELETE_ALL_STUDENTS_COURSES);
    }

    @Override
    public void setNewCourse(int studentID, String courseName) {
        jdbcTemplate.update(SET_NEW_COURSE, studentID, courseName);
    }

    @Override
    public void unlinkCourse(int studentID, String courseToDelete) {
        jdbcTemplate.update(UNLINK_COURSE, studentID, courseToDelete);
    }

    @Override
    public List<Student> getCourseMembers(String courseName) {
        return jdbcTemplate.query(GET_COURSE_MEMBERS, studentRowMapper, courseName);
    }

    @Override
    public List<Course> findAvailableCourses(int studentID) {
        return jdbcTemplate.query(FIND_AVAILABLE_COURSES, courseRowMapper, studentID);
    }

    @Override
    public List<Course> getCoursesOfStudent(int studentID) {
        return jdbcTemplate.query(GET_COURSES_OF_STUDENT ,courseRowMapper, studentID);
    }
}
