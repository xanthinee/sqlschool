package sqltask.courses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqltask.connection.DataConnection;
import sqltask.students.*;
import sqltask.helpers.SQLScriptRunner;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;

class CourseDAOImplTest {

    DataConnection ds = new DataConnection("testdata/connectiontests.properties");
    SQLScriptRunner sqlScriptRunner = new SQLScriptRunner();

    CourseDAO courseDAO = new CourseDAOImpl(ds);

    @BeforeEach
    public void init() {
        try {
            sqlScriptRunner.executeScriptUsingScriptRunner("sqldata/tables_creation.sql", ds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveCourse_whenOneCourseToSave_shouldSaveThisCourse() {

        int courseID = 1;
        Course course = new Course(courseID, "name", "description");
        Course courseToCompare = new Course(null, null, null);
        courseDAO.save(course);

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from courses")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courseToCompare.setId(rs.getInt("course_id"));
                courseToCompare.setName(rs.getString("course_name"));
                courseToCompare.setDescription(rs.getString("course_description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(course, courseToCompare);
    }

    @Test
    void saveAll_whenThereCoursesToSave_shouldSaveAll() {

        List<Course> courses = new ArrayList<>();
        Course course = new Course(1,"a","a");
        Course course1 = new Course(2,"b","b");
        Course course2 = new Course(3,"c","c");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        courseDAO.saveAll(courses);

        List<Course> retrievedCourses = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from courses")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrievedCourses.add(new Course(rs.getInt("course_id"), rs.getString("course_name"),
                        rs.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(courses, retrievedCourses);
    }

    @Test
    void deleteAll_whenThereAreCoursesToDelete_shouldRetrieveEmptyList() {

        List<sqltask.courses.Course> courses = new ArrayList<>();
        Course course = new Course(1,"a","a");
        Course course1 = new Course(2,"b","b");
        Course course2 = new Course(3,"c","c");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into courses values (default,?,?)")) {
            for (Course courseAdd : courses) {
                ps.setString(1,courseAdd.getName());
                ps.setString(2, courseAdd.getDescription());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        courseDAO.deleteAll();

        List<Course> emptyList = new ArrayList<>();
        List<Course> retrievedCourses = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from courses")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrievedCourses.add(new Course(rs.getInt("course_id"), rs.getString("course_name"),
                        rs.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(emptyList, retrievedCourses);
    }

    @Test
    void getAll_whenThereSomeCourses_shouldRetrieveThemAll() {

        List<Course> courses = new ArrayList<>();
        Course course = new Course(1,"a","a");
        Course course1 = new Course(2,"b","b");
        Course course2 = new Course(3,"c","c");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into courses values (default,?,?)")) {
            for (Course courseAdd : courses) {
                ps.setString(1,courseAdd.getName());
                ps.setString(2, courseAdd.getDescription());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(courses, courseDAO.getAll());
    }

    @Test
    void getById_whenSeveralCourses_shouldRetrieveOneWithDeterminedId() {

        List<Course> courses = new ArrayList<>();
        Course course = new Course(1,"a","a");
        Course course1 = new Course(2,"b","b");
        Course course2 = new Course(3,"c","c");
        courses.add(course);
        courses.add(course1);
        courses.add(course2);

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into courses values (default,?,?)")) {
            for (Course courseAdd : courses) {
                ps.setString(1,courseAdd.getName());
                ps.setString(2, courseAdd.getDescription());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(course, courseDAO.getById(1));
    }

    @Test
    void deleteById_whenCourseWithSuchIdDeleted_shouldThrowISE() {

        int courseID = 1;
        Course course = new Course(courseID,"a", "a");
        try (Connection connection = ds.getConnection();
        PreparedStatement ps = connection.prepareStatement("insert  into courses values (default,?,?)")) {
            ps.setString(1,course.getName());
            ps.setString(2, course.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into students values (default,null,?,?)")) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getSurname());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into courses values (default,?,?)")) {
            for (Course courseAdd : courses) {
                ps.setString(1,courseAdd.getName());
                ps.setString(2, courseAdd.getDescription());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        courseDAO.saveStudentsCourses(student, courses);

        List<Course> coursesOfStud = new ArrayList<>();
        try (Connection connection = ds.getConnection();
        PreparedStatement ps = connection.prepareStatement("select c.course_id, c.course_name, c.course_description " +
                "from courses c inner join students_courses sc " +
                "on c.course_id = sc.course_id where student_id = ?")) {
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                coursesOfStud.add(new Course(rs.getInt("course_id"),rs.getString("course_name"),
                        rs.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(courses, coursesOfStud);
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

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into students values (default,null,?,?)")) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getSurname());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into courses values (default,?,?)")) {
            for (Course courseAdd : courses) {
                ps.setString(1,courseAdd.getName());
                ps.setString(2, courseAdd.getDescription());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into public.students_courses values (default,?,?)")) {
            for (Course courseAdd : courses) {
                ps.setInt(1, student.getStudentId());
                ps.setInt(2, courseAdd.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        courseDAO.deleteAllFromStudentsCourses();

        List<Course> emptyList = new ArrayList<>();

        List<Course> retrievedCourses = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select c.course_id, c.course_name, c.course_description " +
                     "from courses c inner join students_courses sc " +
                     "on c.course_id = sc.course_id where student_id = ?")) {
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrievedCourses.add(new Course(rs.getInt("course_id"),rs.getString("course_name"),
                        rs.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(emptyList, retrievedCourses);
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

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into students values (default,null,?,?)")) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getSurname());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into courses values (default,?,?)")) {
            for (Course courseAdd : courses) {
                ps.setString(1,courseAdd.getName());
                ps.setString(2, courseAdd.getDescription());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into public.students_courses values (?,?)")) {
            for (Course courseAdd : courses) {
                ps.setInt(1, student.getStudentId());
                ps.setInt(2, courseAdd.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Course newCourse = new Course(3, "newCourse", "description");
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into courses values (default,?,?)")) {
                ps.setString(1, newCourse.getName());
                ps.setString(2, newCourse.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        courseDAO.setNewCourse(studentID, "newCourse");

        List<Course> enhancedListOfCourses = new ArrayList<>();
        enhancedListOfCourses.add(course);
        enhancedListOfCourses.add(course1);
        enhancedListOfCourses.add(newCourse);

        List<Course> retrievedCourses = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select c.course_id, c.course_name, c.course_description " +
                     "from courses c inner join students_courses sc " +
                     "on c.course_id = sc.course_id where student_id = ?")) {
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrievedCourses.add(new Course(rs.getInt("course_id"),rs.getString("course_name"),
                        rs.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(enhancedListOfCourses, retrievedCourses);
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

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into students values (default,null,?,?)")) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getSurname());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into courses values (default,?,?)")) {
            for (Course courseAdd : courses) {
                ps.setString(1,courseAdd.getName());
                ps.setString(2, courseAdd.getDescription());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into public.students_courses values (?,?)")) {
            for (Course courseAdd : courses) {
                ps.setInt(1, student.getStudentId());
                ps.setInt(2, courseAdd.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        courseDAO.unlinkCourse(studentID, courseToUnlink);
        List<Course> listOfRemainingCourses = new ArrayList<>();
        listOfRemainingCourses.add(course);
        listOfRemainingCourses.add(newCourse);

        List<Course> retrievedCourses = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select c.course_id, c.course_name, c.course_description " +
                     "from courses c inner join students_courses sc " +
                     "on c.course_id = sc.course_id where student_id = ?")) {
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrievedCourses.add(new Course(rs.getInt("course_id"),rs.getString("course_name"),
                        rs.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(listOfRemainingCourses, retrievedCourses);
    }

    @Test
    void getCourseMembers_whenSomeStudentsHaveCourse_shouldRetrieveAllTheseStudents() {

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

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into students values (default,null,?,?)")) {
            for (Student studentAdd : students) {
                ps.setString(1, studentAdd.getName());
                ps.setString(2, studentAdd.getSurname());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into courses values (default,?,?)")) {
            for (Course courseAdd : courses) {
                ps.setString(1,courseAdd.getName());
                ps.setString(2, courseAdd.getDescription());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into public.students_courses values (?,?)")) {
            for (Course courseAdd : courses) {
                ps.setInt(1, student.getStudentId());
                ps.setInt(2, courseAdd.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into public.students_courses values (?,?)")) {
            for (Course courseAdd : courses) {
                ps.setInt(1, student1.getStudentId());
                ps.setInt(2, courseAdd.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into public.students_courses values (?,?)")) {
            for (Course courseAdd : courses) {
                ps.setInt(1, student2.getStudentId());
                ps.setInt(2, courseAdd.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into students values (default,null,?,?)")) {
                ps.setString(1, student.getName());
                ps.setString(2, student.getSurname());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into courses values (default,?,?)")) {
            for (Course courseAdd : courses) {
                ps.setString(1,courseAdd.getName());
                ps.setString(2, courseAdd.getDescription());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        courses.remove(0);


        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into public.students_courses values (?,?)")) {
            for (Course courseAdd : courses) {
                ps.setInt(1, student.getStudentId());
                ps.setInt(2, courseAdd.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Course> availableCourses = new ArrayList<>();
        availableCourses.add(course);
        assertEquals(availableCourses, courseDAO.findAvailableCourses(studentID));
    }
}
