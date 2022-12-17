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
import java.util.stream.Collectors;

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

    public Course getByName(String name) {
        return (Course) em.createQuery("from Course c where c.name = :name")
                .setParameter("name", name)
                .getSingleResult();
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

    @Transactional
    @Override
    public void save(Course entity) {
        em.persist(entity);
    }

    @Transactional
    @Override
    public List<Student> getCourseMembers(String courseName) {
//        String query = "select c.studentSet from " + Course.class.getName() + " c where c.name = :cn";
//        return new ArrayList<>(em.createQuery(query, Student.class)
//                .setParameter("cn",courseName)
//                .);

        Course course = getByName(courseName);

//        Course course = em.find(Course.class, courseName);

        System.out.println(new ArrayList<>(course.getStudentSet()).size());
        return new ArrayList<>(course.getStudentSet());
    }

    @Transactional
    @Override
    public void unlinkCourse(int studentID, String courseToDelete) {
        Student s = em.find(Student.class, studentID);
        Course c = getByName(courseToDelete);
        s.getCoursesOfStud().remove(c);
        c.getStudentSet().remove(s);
    }

    @Transactional
    @Override
    public void setNewCourse(int studentID, String courseName) {
        Student s = em.find(Student.class, studentID);
        Course c = getByName(courseName);
        s.getCoursesOfStud().add(c);
        c.getStudentSet().add(s);
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
