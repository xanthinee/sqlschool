package sqltask.courses;

import sqltask.connection.DataSource;
import sqltask.students.Student;
import sqltask.students.StudentsTableDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class CourseService {

    private final DataSource ds = new DataSource();
    private final Random rd = new Random();
    private final CoursesTableDB dao;

    public CourseService(CoursesTableDB dao) {
        this.dao = dao;
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    public List<Course> getAll() {
        return dao.getAll();
    }

    public List<Course> getCoursesOfStudent(int studID) {
        return dao.getCoursesOfStudent(studID);
    }

    public List<Course> findAvailableCourses(int studentID) {
        return dao.findAvailableCourses(studentID);
    }

    public List<Student> getCourseMembers(String courseName) {
        return dao.getCourseMembers(courseName);
    }

    public void unlinkCourse(int studentID, String courseToDelete) {
        dao.unlinkCourse(studentID, courseToDelete);
    }

    public void setNewCourse(int studentID, String courseName) {
        dao.setNewCourse(studentID, courseName);
    }

    public Course getById(int id) {
        return dao.getById(id);
    }

    public void deleteById(int id) {
        dao.deleteById(id);
    }

    public void createStdCrsTable() {

        StudentsTableDB studentsDB = new StudentsTableDB(ds);
        CoursesTableDB coursesDb = new CoursesTableDB(ds, "courses", "students_courses");
        List<Student> students = studentsDB.getAll();
        List<Course> courses = coursesDb.getAll();

        for (Student student : students) {
            int numOfCourses = rd.nextInt(1, 4);
            try (Connection con = ds.getConnection();
                 PreparedStatement st = con.prepareStatement("insert into public.students_courses values (default,?,?)")) {
                for (int i = 0; i < numOfCourses; i++ ) {
                    st.setInt(1, student.getStudentId());
                    st.setInt(2, courses.get(rd.nextInt(0, courses.size())).getId());
                    st.addBatch();
                }
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
