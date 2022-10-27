package sqltask.courses;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqltask.connection.DataSource;
import sqltask.groups.Group;
import sqltask.students.*;
import sqltask.helpers.SQLScriptRunner;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.*;

class CourseDAOImplTest {

    DataSource ds = new DataSource("testdata/connectiontests.properties");
    SQLScriptRunner sqlScriptRunner = new SQLScriptRunner();

    CourseDAO courseDAO = new CourseDAOImpl(ds);
    StudentDAO studentDAO = new StudentDAOImpl(ds);

    @BeforeEach
    public void init() {
        try {
            DataSource ds = new DataSource("testdata/connectiontests.properties");
            sqlScriptRunner.executeScriptUsingScriptRunner("sqltestdata/test_table_creation.sql", ds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void destroy() {
        try {
            DataSource ds = new DataSource("testdata/connectiontests.properties");
            sqlScriptRunner.executeScriptUsingScriptRunner("sqltestdata/test_table_delete.sql", ds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveCourse_whenOneCourseToSave_shouldSaveThisCourse() {

        int courseID = 1;
        sqltask.courses.Course course = new sqltask.courses.Course(courseID, "name", "description");
        courseDAO.saveCourse(course);
        assertEquals(course, courseDAO.getById(courseID));
    }

    @Test
    void saveAll_whenThereCoursesToSave_shouldSaveAll() {

        List<sqltask.courses.Course> courses = new ArrayList<>();
        sqltask.courses.Course course = new sqltask.courses.Course(1,"a","a");
        sqltask.courses.Course course1 = new sqltask.courses.Course(2,"a","a");
        sqltask.courses.Course course2 = new sqltask.courses.Course(3,"a","a");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        courseDAO.saveAll(courses);
        assertEquals(courses, courseDAO.getAll());
    }

    @Test
    void deleteAll_whenThereAreCoursesToDelete_shouldRetrieveEmptyList() {

        List<sqltask.courses.Course> courses = new ArrayList<>();
        sqltask.courses.Course course = new sqltask.courses.Course(1,"a","a");
        sqltask.courses.Course course1 = new sqltask.courses.Course(2,"a","a");
        sqltask.courses.Course course2 = new sqltask.courses.Course(3,"a","a");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        List<sqltask.courses.Course> emptyList = new ArrayList<>();

        courseDAO.saveAll(courses);
        courseDAO.deleteAll();
        assertEquals(emptyList, courseDAO.getAll());
    }

    @Test
    void getAll_whenThereSomeCourses_shouldRetrieveThemAll() {

        List<sqltask.courses.Course> courses = new ArrayList<>();
        sqltask.courses.Course course = new sqltask.courses.Course(1,"a","a");
        sqltask.courses.Course course1 = new sqltask.courses.Course(2,"a","a");
        sqltask.courses.Course course2 = new sqltask.courses.Course(3,"a","a");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        courseDAO.saveAll(courses);
        assertEquals(courses, courseDAO.getAll());
    }

    @Test
    void getById_whenSeveralCourses_shouldRetrieveOneWithDeterminedId() {

        List<sqltask.courses.Course> courses = new ArrayList<>();
        sqltask.courses.Course course = new sqltask.courses.Course(1,"a","a");
        sqltask.courses.Course course1 = new sqltask.courses.Course(2,"a","a");
        sqltask.courses.Course course2 = new sqltask.courses.Course(3,"a","a");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        courseDAO.saveAll(courses);
        assertEquals(course, courseDAO.getById(1));
    }

    @Test
    void deleteById_whenCourseWithSuchIdDeleted_shouldThrowISE() {

        int courseID = 1;
        Course course = new Course(courseID,"a", "a");
        courseDAO.saveCourse(course);
        courseDAO.deleteById(courseID);
        assertThrows(IllegalStateException.class, ()-> {courseDAO.getById(courseID);},
                "No data found for id " + courseID);
    }

    @Test
    void save_whenThereStudentAndCoursesToSave_shouldSaveThem() {

        int studentID = 1;
        Student student = new Student(studentID,1,"a", "a");
        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "algb", "description");
        Course course1 = new Course(2, "history", "description");
        courses.add(course);
        courses.add(course1);

        studentDAO.save(student);
        courseDAO.saveAll(courses);
        courseDAO.save(student, courses);
        assertEquals(courses, courseDAO.getCoursesOfStudent(studentID));
    }

    @Test
    void deleteAllFromStudentsCourses_whenThereRowsToDelete_shouldDeleteThemAll() {

        int studentID = 1;
        Student student = new Student(studentID,1,"a", "a");
        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "algb", "description");
        Course course1 = new Course(2, "history", "description");
        courses.add(course);
        courses.add(course1);

        studentDAO.save(student);
        courseDAO.saveAll(courses);
        courseDAO.save(student, courses);
        courseDAO.deleteAllFromStudentsCourses();
        List<Course> emptyList = new ArrayList<>();
        assertEquals(emptyList, courseDAO.getCoursesOfStudent(studentID));
    }

    @Test
    void setNewCourse_whenThereNewCourseToSet_shouldSetNewCourse() {

        int studentID = 1;
        Student student = new Student(studentID,1,"a", "a");
        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "algb", "description");
        Course course1 = new Course(2, "history", "description");
        courses.add(course);
        courses.add(course1);

        studentDAO.save(student);
        courseDAO.saveAll(courses);
        courseDAO.save(student, courses);

        Course newCourse = new Course(3, "newCourse", "description");
        courseDAO.saveCourse(newCourse);
        courseDAO.setNewCourse(studentID, "newCourse");

        List<Course> enhancedListOfCourses = new ArrayList<>();
        enhancedListOfCourses.add(course);
        enhancedListOfCourses.add(course1);
        enhancedListOfCourses.add(newCourse);

        assertEquals(enhancedListOfCourses, courseDAO.getCoursesOfStudent(studentID));
    }

    @Test
    void unlinkCourse_whenThereCourseToUnlink_shouldUnlinkThisCourse() {

        int studentID = 1;
        String courseToUnlink = "history";
        Student student = new Student(studentID,1,"a", "a");
        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "algb", "description");
        Course course1 = new Course(2, courseToUnlink, "description");
        Course newCourse = new Course(3, "newCourse", "description");
        courses.add(course);
        courses.add(course1);
        courses.add(newCourse);

        studentDAO.save(student);
        courseDAO.saveAll(courses);
        courseDAO.save(student, courses);

        courseDAO.unlinkCourse(studentID, courseToUnlink);
        List<Course> listOfRemainingCourses = new ArrayList<>();
        listOfRemainingCourses.add(course);
        listOfRemainingCourses.add(newCourse);

        assertEquals(listOfRemainingCourses, courseDAO.getCoursesOfStudent(studentID));
    }

    @Test
    void getCourseMembers_whenSomeStudentsHaveCourse_shouldRetrieveAllTheseStudents() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1,0,"a","a");
        Student student1 = new Student(2,0,"a","a");
        Student student2 = new Student(3,0,"a","a");
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

        studentDAO.saveAll(students);
        courseDAO.saveAll(courses);
        courseDAO.save(student, courses);
        courseDAO.save(student1, courses);
        courseDAO.save(student2, courses);
        assertEquals(students, courseDAO.getCourseMembers("history"));
    }

    @Test
    void findAvailableCourses_whenThereSomeAvbCourses_shouldRetrieveThem() {

        int studentID = 1;
        Student student = new Student(studentID,0,"a","a");

        List<Course> courses = new ArrayList<>();
        Course course = new Course(1, "mathematics", "description");
        Course course1 = new Course(2, "history", "description");
        Course newCourse = new Course(3, "newCourse", "description");
        courses.add(course);
        courses.add(course1);
        courses.add(newCourse);

        studentDAO.save(student);
        courseDAO.saveAll(courses);

        courses.remove(0);
        courseDAO.save(student, courses);

        List<Course> availableCourses = new ArrayList<>();
        availableCourses.add(course);
        assertEquals(availableCourses, courseDAO.findAvailableCourses(studentID));
    }
}
