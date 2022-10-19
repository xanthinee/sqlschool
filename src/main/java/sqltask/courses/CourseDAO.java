package sqltask.courses;

import sqltask.applicationmenu.DAO;
import sqltask.students.Student;

import java.util.List;

public interface CourseDAO extends DAO<Course> {

    List<Student> getCourseMembers(String courseName);
    void unlinkCourse(int studentID, String courseToDelete);
    void setNewCourse(int studentID, String courseName);
    List<Course> findAvailableCourses(int studentID);
    List<Course> getCoursesOfStudent(int studentID);
    void putCoursesInTable(List<Course> courses);
    void deleteAllFromStudentsCourses();
    void save(Student student, List<Course> courses);
}
