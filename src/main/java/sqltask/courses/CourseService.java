package sqltask.courses;

import org.springframework.stereotype.Service;
import sqltask.helpers.CustomFileReader;
import sqltask.students.Student;
import sqltask.students.StudentDAO;

import java.util.*;
import java.util.stream.Stream;

@Service
public class CourseService {
    private final Random rd;
    private final CourseDAO courseDao;
    private final StudentDAO studentDao;
    private static final int MAX_COURSES_PER_STUDENT = 3;

    public CourseService(CourseDAO courseDao, StudentDAO studentDao, Random rd) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.rd = rd;
    }

    public void deleteAll() {
        courseDao.deleteAll();
    }

    public List<Course> getAll() {
        return courseDao.getAll();
    }

    public List<Course> getCoursesOfStudent(int studID) {
        return courseDao.getCoursesOfStudent(studID);
    }

    public List<Course> findAvailableCourses(int studentID) {
        return courseDao.findAvailableCourses(studentID);
    }

    public List<Student> getCourseMembers(String courseName) {
        return courseDao.getCourseMembers(courseName);
    }

    public void unlinkCourse(int studentID, String courseToDelete) {
        courseDao.unlinkCourse(studentID, courseToDelete);
    }

    public void setNewCourse(int studentID, String courseName) {
        courseDao.setNewCourse(studentID, courseName);
    }

    public Course getById(int id) {
        return courseDao.getById(id);
    }

    public void deleteById(int id) {
        courseDao.deleteById(id);
    }
    public void saveAll(List<Course> courses) {
        courseDao.saveAll(courses);
    }
    private List<Course> selectRandomCourses(List<Course> allCourses) {

        List<Course> availableCourses = new LinkedList<>(allCourses);
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < MAX_COURSES_PER_STUDENT + 1; i++) {
            Course course = availableCourses.remove(rd.nextInt(0, availableCourses.size()));
            courses.add(course);
        }
        availableCourses.addAll(courses);
        return courses;
    }

    public void createStdCrsTable() {

        List<Student> students = studentDao.getAll();
        List<Course> courses = courseDao.getAll();

        for (Student student : students) {
            courseDao.saveStudentsCourses(student, selectRandomCourses(courses));
        }
    }

    public List<Course> makeCoursesList(String fileName) {

        CustomFileReader fileCon = new CustomFileReader();
        Stream<String> courses = fileCon.readFile(fileName);
        CoursesParser cp = new CoursesParser();
        return courses.map(cp::parse).toList();
    }

    public void deleteAllFromStudentsCourses() {
        courseDao.deleteAllFromStudentsCourses();
    }
}
