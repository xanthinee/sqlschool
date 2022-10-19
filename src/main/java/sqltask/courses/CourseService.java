package sqltask.courses;

import sqltask.connection.DataSource;
import sqltask.students.Student;
import sqltask.students.StudentDAOImpl;

import java.util.*;

public class CourseService {

    private final DataSource ds = new DataSource();
    private final Random rd = new Random();
    private final CourseDAOImpl dao;
    private final StudentDAOImpl studentDAO;

    public CourseService(CourseDAOImpl dao, StudentDAOImpl studentDAO) {
        this.dao = dao;
        this.studentDAO = studentDAO;
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

        List<Student> students = studentDAO.getAll();
        CourseDAOImpl courseDAO = new CourseDAOImpl(ds);
        List<Course> courses = courseDAO.getAll();

        for (Student student : students) {
            courseDAO.save(student, courses);
        }
    }

}
