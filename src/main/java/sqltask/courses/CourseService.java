package sqltask.courses;

import org.springframework.stereotype.Service;
import sqltask.students.Student;
import sqltask.students.StudentDAO;

import java.util.*;

@Service
public class CourseService {

    private final Random rd = new Random();
    private final CourseDAO courseDAOJdbc;
    private final StudentDAO studentDAOJdbc;
    private static final int MAX_COURSES_PER_STUDENT = 3;

    public CourseService(CourseDAO courseDAOJdbc, StudentDAO studentDAOJdbc) {
        this.courseDAOJdbc = courseDAOJdbc;
        this.studentDAOJdbc = studentDAOJdbc;
    }

    public void deleteAll() {
        courseDAOJdbc.deleteAll();
    }

    public List<Course> getAll() {
        return courseDAOJdbc.getAll();
    }

    public List<Course> getCoursesOfStudent(int studID) {
        return courseDAOJdbc.getCoursesOfStudent(studID);
    }

    public List<Course> findAvailableCourses(int studentID) {
        return courseDAOJdbc.findAvailableCourses(studentID);
    }

    public List<Student> getCourseMembers(String courseName) {
        return courseDAOJdbc.getCourseMembers(courseName);
    }

    public void unlinkCourse(int studentID, String courseToDelete) {
        courseDAOJdbc.unlinkCourse(studentID, courseToDelete);
    }

    public void setNewCourse(int studentID, String courseName) {
        courseDAOJdbc.setNewCourse(studentID, courseName);
    }

    public Course getById(int id) {
        return courseDAOJdbc.getById(id);
    }

    public void deleteById(int id) {
        courseDAOJdbc.deleteById(id);
    }
    private List<Course> selectRandomCourses(List<Course> allCourses) {

        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < MAX_COURSES_PER_STUDENT + 1; i++) {
            courses.add(allCourses.get(rd.nextInt(0, allCourses.size())));
        }
        return courses;
    }

    public void createStdCrsTable() {

        List<Student> students = studentDAOJdbc.getAll();
        List<Course> courses = courseDAOJdbc.getAll();

        for (Student student : students) {
            courseDAOJdbc.save(student, selectRandomCourses(courses));
        }
    }

}
