package sqltask.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import sqltask.students.Student;
import sqltask.students.StudentDAOJpa;

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

    @Autowired
    StudentDAOJpa studentDao;

    private Course getByName(String name) {
        return (Course) em.createQuery("from Course c where c.name = :name")
                .setParameter("name", name)
                .getSingleResult();
    }

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

    @Transactional
    @Override
    public List<Student> getCourseMembers(String courseName) {
        Course course = getByName(courseName);
        return new ArrayList<>(course.getStudentSet());
    }

    @Transactional
    @Override
    public void unlinkCourse(int studentID, String courseToDelete) {
        Student student = em.find(Student.class, studentID);
        Course course = getByName(courseToDelete);
        student.getCoursesOfStud().remove(course);
        course.getStudentSet().remove(student);
    }

    @Transactional
    @Override
    public void setNewCourse(int studentID, String courseName) {
        Student student = em.find(Student.class, studentID);
        Course course = getByName(courseName);
        student.getCoursesOfStud().add(course);
        course.getStudentSet().add(student);
    }

    @Transactional
    @Override
    public List<Course> findAvailableCourses(int studentID) {

        Student s = em.find(Student.class, studentID);
        List<Course> courses = getAll();
        courses.removeAll(s.getCoursesOfStud());
        return courses;
    }

    @Transactional
    @Override
    public List<Course> getCoursesOfStudent(int studentID) {
        Student student = em.find(Student.class, studentID);
        return new ArrayList<>(student.getCoursesOfStud());
    }

    @Transactional
    @Override
    public void deleteAllFromStudentsCourses() {

        List<Student> students = studentDao.getAll();
        for (Student student : students) {
            student.getCoursesOfStud().clear();
        }
        List<Course> courses = getAll();
        for (Course course : courses) {
            course.getStudentSet().clear();
        }
    }

    @Transactional
    @Override
    public void saveStudentsCourses(Student student, List<Course> courses) {
        Set<Course> coursesToSave = new HashSet<>(courses);
        student.setCoursesOfStud(coursesToSave);
        em.merge(student);
    }
}
