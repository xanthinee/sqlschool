package sqltask.courses;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import sqltask.students.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Profile("jpa")
@SuppressWarnings("unchecked")
public class CourseDAOJpa implements CourseDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Course getById(int id) {
        return em.find(Course.class, id);
    }

    @Override
    public List<Course> getAll() {
        return em.createQuery("select c from Course c", Course.class).getResultList();
    }

    @Transactional
    @Override
    public void deleteAll() {
        for (Course course : getAll()) {
            em.remove(course);
        }
    }

    @Transactional
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

    @Override
    public void save(Course entity) {
        em.persist(entity);
    }

    @Override
    public List<Student> getCourseMembers(String courseName) {
        String query = "select s from students s inner join students_courses sc on sc.student_id = s.student_id inner join courses c on sc.course_id = c.course_id where c.course_name = ?1";
        return em.createNativeQuery(query, Student.class)
                .setParameter(1,courseName)
                .getResultList();
    }

    @Override
    public void unlinkCourse(int studentID, String courseToDelete) {
        String query = "delete from students_courses sc using courses c where c.course_id = sc.course_id and sc.student_id = ?1 and c.course_name = ?2";
        em.createNativeQuery(query)
                .setParameter(1, studentID)
                .setParameter(2, courseToDelete)
                .executeUpdate();
    }

    @Transactional
    @Override
    public void setNewCourse(int studentID, String courseName) {
        String query = "insert into students_courses (student_id, course_id) select ?1, course_id from courses where course_name = ?2";
        em.createNativeQuery(query)
                .setParameter(1, studentID)
                .setParameter(2, courseName)
                .executeUpdate();
    }

    @Override
    public List<Course> findAvailableCourses(int studentID) {

        String query = "select c.course_id, c.course_name, c.course_description from courses c where c.course_id not in (select sc.course_id from students_courses sc where sc.student_id = :1)";
        return em.createNativeQuery(query, Course.class)
                .setParameter(1, studentID)
                .getResultList();
    }

    @Override
    public List<Course> getCoursesOfStudent(int studentID) {
        Student student = em.find(Student.class, studentID);
        return new ArrayList<>(student.getCoursesOfStud());
    }

    @Transactional
    @Override
    public void deleteAllFromStudentsCourses() {
        String query = "delete from students_courses";
        em.createNativeQuery(query);
    }

    @Transactional
    @Override
    public void saveStudentsCourses(Student student, List<Course> courses) {
        Set<Course> coursesToSave = new HashSet<>(courses);
        student.setCoursesOfStud(coursesToSave);
        em.merge(student);
    }
}
