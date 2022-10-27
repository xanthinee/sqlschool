package sqltask.courses;

import sqltask.connection.DataSource;
import sqltask.students.Student;
import sqltask.students.StudentDAO;

import java.util.*;

public class CourseService {

    private final Random rd = new Random();
    private final CourseDAO courseDAO;
    private final StudentDAO studentDAO;

    public CourseService(CourseDAO courseDAO, StudentDAO studentDAO) {
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
    }

    public void deleteAll() {
        courseDAO.deleteAll();
    }

    public List<Course> getAll() {
        return courseDAO.getAll();
    }

    public List<Course> getCoursesOfStudent(int studID) {
        return courseDAO.getCoursesOfStudent(studID);
    }

    public List<Course> findAvailableCourses(int studentID) {
        return courseDAO.findAvailableCourses(studentID);
    }

    public List<Student> getCourseMembers(String courseName) {
        return courseDAO.getCourseMembers(courseName);
    }

    public void unlinkCourse(int studentID, String courseToDelete) {
        courseDAO.unlinkCourse(studentID, courseToDelete);
    }

    public void setNewCourse(int studentID, String courseName) {
        courseDAO.setNewCourse(studentID, courseName);
    }

    public Course getById(int id) {
        return courseDAO.getById(id);
    }

    public void deleteById(int id) {
        courseDAO.deleteById(id);
    }

    private List<Course> selectRandomCourses(List<Course> allCourses) {

        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < rd.nextInt(1,4); i++) {
            courses.add(allCourses.get(rd.nextInt(0, allCourses.size())));
        }
        return courses;
    }

    public void createStdCrsTable() {

        List<Student> students = studentDAO.getAll();
        List<Course> courses = courseDAO.getAll();

        for (Student student : students) {
            courseDAO.save(student, selectRandomCourses(courses));
        }
    }

}
