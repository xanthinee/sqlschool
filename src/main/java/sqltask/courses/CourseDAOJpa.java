package sqltask.courses;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import sqltask.students.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Profile("hibernate")
public class CourseDAOJpa implements CourseDAO {


    private static final String GET_ALL = "select c from courses c";
    @PersistenceContext
    private EntityManager em;

    @Override
    public Course getById(int id) {
        return em.find(Course.class, id);
    }

    @Override
    public List<Course> getAll() {
        return em.createNamedQuery("course.getAll", Course.class).getResultList();
    }

    @Override
    public void deleteAll() {
        for (Course course : getAll()) {
            em.remove(course);
        }
    }

    @Override
    public void deleteById(int id) {
        Course course = em.find(Course.class, id);
        em.remove(course);
    }

    @Transactional
    @Override
    public void saveAll(List<Course> list) {
        for (Course course : list) {
            em.persist(course);
        }
    }

    @Transactional
    @Override
    public void save(Course entity) {
        em.persist(entity);
    }

    @Override
    public List<Student> getCourseMembers(String courseName) {
        return null;
    }

    @Override
    public void unlinkCourse(int studentID, String courseToDelete) {

    }

    @Override
    public void setNewCourse(int studentID, String courseName) {

    }

    @Override
    public List<Course> findAvailableCourses(int studentID) {
        return null;
    }

    @Override
    public List<Course> getCoursesOfStudent(int studentID) {
        return null;
    }

    @Override
    public void deleteAllFromStudentsCourses() {

    }

    @Override
    public void saveStudentsCourses(Student student, List<Course> courses) {

    }
}
