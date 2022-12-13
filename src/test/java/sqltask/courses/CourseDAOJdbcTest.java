package sqltask.courses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import sqltask.ContainersConfig;
import sqltask.TestDAOInterface;
import sqltask.students.Student;
import sqltask.students.StudentDAOJdbc;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ContextConfiguration(initializers = {ContainersConfig.Initializer.class})
@SpringBootTest(classes = {ContainersConfig.class})
@ActiveProfiles(profiles = {"test","jdbc"})
class CourseDAOJdbcTest implements TestDAOInterface {

    @Autowired
    ApplicationContext ctx;
    @Autowired
    CourseDAOJdbc dao;
    @Autowired
    StudentDAOJdbc studentDao;
    @Autowired
    JdbcTemplate jdbc;

    private static final String COURSE_TABLE = "courses";
    private static final String STUDENT_COURSE = "students_courses";
    private static final String STUDENT_TABLE = "students";

    @BeforeEach
    public void clearContainer() {
        JdbcTestUtils.deleteFromTables(jdbc, STUDENT_TABLE, COURSE_TABLE, STUDENT_COURSE);
    }

    @Override
    @Test
    public void save_shouldSaveOnlyOneLine() {
        dao.save(new Course(1,"a", "a"));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, COURSE_TABLE));
    }

    @Override
    @Test
   public void saveAll_shouldSaveSeveralRows() {

        List<Course> courses = new ArrayList<>();
        Course course = new Course(1,"a","a");
        Course course1 = new Course(2,"b","b");
        Course course2 = new Course(3,"c","c");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        dao.saveAll(courses);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbc, COURSE_TABLE));
    }

    @Override
    @Test
    public void deleteAll_shouldRetrieveZeroRows() {

        List<Course> courses = new ArrayList<>();
        Course course = new Course(1,"a","a");
        Course course1 = new Course(2,"b","b");
        Course course2 = new Course(3,"c","c");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        jdbc.batchUpdate("insert into " + COURSE_TABLE + " values (default, ?, ?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
            ps.setString(1, courseJdbc.getName());
            ps.setString(2,courseJdbc.getDescription());
                });
        dao.deleteAll();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, COURSE_TABLE));
    }

    @Override
    @Test
    public void getAll_sizesShouldBeEqual(){

        List<Course> courses = new ArrayList<>();
        Course course = new Course(1,"a","a");
        Course course1 = new Course(2,"b","b");
        Course course2 = new Course(3,"c","c");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        jdbc.batchUpdate("insert into " + COURSE_TABLE + " values (default, ?, ?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setString(1, courseJdbc.getName());
                    ps.setString(2,courseJdbc.getDescription());
                });
        assertEquals(courses.size(), dao.getAll().size());
    }

    @Override
    @Test
    public void getById_shouldRetrieveExactEntity() {

        int courseId = 10;
        Course course = new Course(courseId, "a", "a");
        jdbc.update("insert into " + COURSE_TABLE + " values (?,?,?)", course.getId(), course.getName(), course.getDescription());
        assertEquals(course, dao.getById(courseId));
    }

    @Override
    @Test
    public void deleteById_shouldCountZeroRows() {

        int courseId = 10;
        Course course = new Course(courseId, "a", "a");
        jdbc.update("insert into " + COURSE_TABLE + " values (?,?,?)", course.getId(), course.getName(), course.getDescription());
        dao.deleteById(courseId);
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, COURSE_TABLE, "course_id =" + courseId));
    }

    @Test
    void save_shouldRetrieveTwoRows() {

        int studentID = 1;
        Student student = new Student(studentID,1,"a", "a");
        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "algb", "description");
        Course course1 = new Course(2, "history", "description");
        courses.add(course);
        courses.add(course1);

        jdbc.update("insert into " + STUDENT_TABLE + " values (?,?,?,?)", student.getStudentId(), student.getGroupId(),
                student.getName(), student.getSurname());

        jdbc.batchUpdate("insert into " + COURSE_TABLE + " values (default,?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                ps.setString(1, courseJdbc.getName());
                ps.setString(2, courseJdbc.getDescription());
                });

        dao.saveStudentsCourses(student, courses);
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbc, STUDENT_COURSE));
    }

    @Test
    void deleteAllFromStudentsCourses_shouldRetrieveZeroRows() {

        int studentID = 1;
        Student student = new Student(studentID,1,"a", "a");
        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "algb", "description");
        Course course1 = new Course(2, "history", "description");
        courses.add(course);
        courses.add(course1);

        jdbc.update("insert into " + STUDENT_TABLE + " values (?,?,?,?)", student.getStudentId(), student.getGroupId(),
                student.getName(), student.getSurname());

        jdbc.batchUpdate("insert into " + COURSE_TABLE + " values (default,?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setString(1, courseJdbc.getName());
                    ps.setString(2, courseJdbc.getDescription());
                });
        jdbc.batchUpdate("insert into " + STUDENT_COURSE + " values (default, ?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
            ps.setInt(1, student.getStudentId());
            ps.setInt(2, courseJdbc.getId());
                });

        dao.deleteAllFromStudentsCourses();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, STUDENT_COURSE));
    }

    @Test
    void setNewCourse_shouldRetrieveThreeRows() {

        int studentID = 1;
        Student student = new Student(studentID,1,"a", "a");
        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "algb", "description");
        Course course1 = new Course(2, "history", "description");
        courses.add(course);
        courses.add(course1);

        jdbc.update("insert into " + STUDENT_TABLE + " values (?,?,?,?)", student.getStudentId(), student.getGroupId(),
                student.getName(), student.getSurname());

        jdbc.batchUpdate("insert into " + COURSE_TABLE + " values (default,?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setString(1, courseJdbc.getName());
                    ps.setString(2, courseJdbc.getDescription());
                });
        jdbc.batchUpdate("insert into " + STUDENT_COURSE + " values (default, ?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setInt(1, student.getStudentId());
                    ps.setInt(2, courseJdbc.getId());
                });

        Course newCourse = new Course(99, "newCourse", "description");
        jdbc.update("insert into " + COURSE_TABLE + " values (?,?,?)", newCourse.getId(), newCourse.getName(),
                newCourse.getDescription());
        dao.setNewCourse(studentID, newCourse.getName());
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbc, STUDENT_COURSE));
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc,STUDENT_COURSE,"course_id = 99"));
    }

    @Test
    void unlinkCourse_shouldRetrieveZeroRows() {

        int studentID = 1;
        String courseNameToDelete = "history";
        int idOfCourseToDelete = 10;
        Student student = new Student(studentID,1,"a", "a");
        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "algb", "description");
        Course course1 = new Course(idOfCourseToDelete, courseNameToDelete, "description");
        courses.add(course);
        courses.add(course1);

        jdbc.update("insert into " + STUDENT_TABLE + " values (?,?,?,?)", student.getStudentId(), student.getGroupId(),
                student.getName(), student.getSurname());

        jdbc.batchUpdate("insert into " + COURSE_TABLE + " values (?,?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setInt(1, courseJdbc.getId());
                    ps.setString(2, courseJdbc.getName());
                    ps.setString(3, courseJdbc.getDescription());
                });
        jdbc.batchUpdate("insert into " + STUDENT_COURSE + " values (default, ?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setInt(1, student.getStudentId());
                    ps.setInt(2, courseJdbc.getId());
                });
        dao.unlinkCourse(studentID, courseNameToDelete);
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, STUDENT_COURSE, "course_id =" + idOfCourseToDelete));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, STUDENT_COURSE));
    }

    @Test
    void getCourseMembers_shouldRetrieveAllTheseStudents() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1,0,"a","a");
        Student student1 = new Student(2,0,"b","b");
        Student student2 = new Student(3,0,"c","c");
        students.add(student);
        students.add(student1);
        students.add(student2);

        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "mathematics", "description");
        Course course1 = new Course(2, "history", "description");
        Course newCourse = new Course(3, "newCourse", "description");
        courses.add(course);
        courses.add(course1);
        courses.add(newCourse);

        jdbc.batchUpdate("insert into " + STUDENT_TABLE + " values (?,?,?,?)", students, students.size(),
                (PreparedStatement ps, Student studentJdbc) -> {
                    ps.setInt(1, studentJdbc.getStudentId());
                    ps.setInt(2, studentJdbc.getGroupId());
                    ps.setString(3, studentJdbc.getName());
                    ps.setString(4, studentJdbc.getSurname());
                });
        jdbc.batchUpdate("insert into " + COURSE_TABLE + " values (?,?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setInt(1, courseJdbc.getId());
                    ps.setString(2, courseJdbc.getName());
                    ps.setString(3, courseJdbc.getDescription());
                });
        jdbc.batchUpdate("insert into " + STUDENT_COURSE + " values (default,?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setInt(1, student.getStudentId());
                    ps.setInt(2, courseJdbc.getId());
                });
        jdbc.batchUpdate("insert into " + STUDENT_COURSE + " values (default,?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setInt(1, student1.getStudentId());
                    ps.setInt(2, courseJdbc.getId());
                });
        jdbc.batchUpdate("insert into " + STUDENT_COURSE + " values (default,?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setInt(1, student2.getStudentId());
                    ps.setInt(2, courseJdbc.getId());
                });

        assertEquals(3, JdbcTestUtils.countRowsInTableWhere(jdbc, STUDENT_COURSE, "course_id =" + 2));
    }

    @Test
    void findAvailableCourses_shouldRetrieveOneRow() {

        int studentID = 1;
        Student student = new Student(studentID,1,"a", "a");
        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "algb", "description");
        Course course1 = new Course(2, "history", "description");
        Course course2 = new Course(3,"Musik", "description");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        jdbc.update("insert into " + STUDENT_TABLE + " values (?,?,?,?)", student.getStudentId(), student.getGroupId(),
                student.getName(), student.getSurname());

        jdbc.batchUpdate("insert into " + COURSE_TABLE + " values (?,?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setInt(1, courseJdbc.getId());
                    ps.setString(2, courseJdbc.getName());
                    ps.setString(3, courseJdbc.getDescription());
                });

        courses.remove(0);

        jdbc.batchUpdate("insert into " + STUDENT_COURSE + " values (default, ?,?)", courses, courses.size(),
                (PreparedStatement ps, Course courseJdbc) -> {
                    ps.setInt(1, student.getStudentId());
                    ps.setInt(2, courseJdbc.getId());
                });

        assertEquals(1, dao.findAvailableCourses(studentID).size());
        assertTrue(dao.findAvailableCourses(studentID).contains(course));
    }
}
