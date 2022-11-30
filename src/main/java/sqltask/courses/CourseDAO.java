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
    void deleteAllFromStudentsCourses();
    void saveStudentsCourses(Student student, List<Course> courses);
}
