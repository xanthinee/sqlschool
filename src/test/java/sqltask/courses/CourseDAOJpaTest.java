package sqltask.courses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import sqltask.ContainersConfig;
import sqltask.TestDAOInterface;
import sqltask.students.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ContextConfiguration(initializers = {ContainersConfig.Initializer.class})
@SpringBootTest(classes = ContainersConfig.class)
@ActiveProfiles(profiles = {"test", "jpa"})
public class CourseDAOJpaTest implements TestDAOInterface {
    @Autowired
    EntityManager entityManager;

    @Autowired
    CourseDAOJpa courseDao;
    @Autowired
    JdbcTemplate jdbc;

    @BeforeEach
    public void clearContainer() {
        JdbcTestUtils.deleteFromTables(jdbc, "students_courses","courses", "students");
    }
    @Override
    @Test
    public void save_shouldSaveOnlyOneLine() {
        Course course = new Course(1,"name1", "description1");
        courseDao.save(course);
        assertNotNull(entityManager.find(Course.class, course.getId()));
    }

    @Override
    @Test
    public void saveAll_shouldSaveSeveralRows() {
        List<Course> courses = new ArrayList<>(Arrays.asList(
                new Course(1,"name1", "description1"),
                new Course(2,"name2", "description2"),
                new Course(3,"name3", "description3")
        ));
        courseDao.saveAll(courses);
        assertEquals(3, entityManager.createQuery("select c from Course c").getResultList().size());
    }

    @Override
    @Test
    @Transactional
    public void deleteAll_shouldRetrieveZeroRows() {
        List<Course> courses = new ArrayList<>(Arrays.asList(
                new Course(1,"name1", "description1"),
                new Course(2,"name2", "description2"),
                new Course(3,"name3", "description3")
        ));
        for (Course course : courses) {
            entityManager.persist(course);
        }
        courseDao.deleteAll();
        assertEquals(0,entityManager.createQuery("select c from Course c").getResultList().size());
    }

    @Override
    @Test
    public void getAll_sizesShouldBeEqual() {
        List<Course> courses = new ArrayList<>(Arrays.asList(
                new Course(1,"name1", "description1"),
                new Course(2,"name2", "description2"),
                new Course(3,"name3", "description3")
        ));
        for (Course course : courses) {
            entityManager.persist(course);
        }
        assertEquals(courses.size(), courseDao.getAll().size());
    }

    @Override
    @Test
    public void getById_shouldRetrieveExactEntity() {
        Course course = new Course(1, "name", "description");
        entityManager.persist(course);
        assertEquals(course, courseDao.getById(course.getId()));
    }

    @Override
    @Test
    public void deleteById_shouldCountZeroRows() {
        Course course = new Course(1, "name", "description");
        entityManager.persist(course);
        courseDao.deleteById(course.getId());
        assertNull(entityManager.find(Course.class, course.getId()));
    }

    @Test
    void deleteAllFromStudentsCourses_shouldRetrieveZeroRows() {

        entityManager.merge(new Student(1,1,"name", "description"));
        Student student = entityManager.find(Student.class, 1);

        entityManager.merge(new Course(1, "algb", "description"));
        entityManager.merge(new Course(2, "musik", "description"));
        Course algebra = entityManager.find(Course.class, 1);
        Course music = entityManager.find(Course.class, 2);

        algebra.setStudentSet(new HashSet<>(List.of(student)));
        music.setStudentSet(new HashSet<>(List.of(student)));
        student.setCoursesOfStud(new HashSet<>(Arrays.asList(algebra, music)));

        assertEquals(2, student.getCoursesOfStud().size());
        courseDao.deleteAllFromStudentsCourses();
        assertEquals(0, student.getCoursesOfStud().size());
    }

    @Test
    void setNewCourse_shouldRetrieveThreeRows() {
        entityManager.merge(new Student(1,1,"name", "surname"));
        Student student = entityManager.find(Student.class, 1);
        entityManager.merge(new Course(1,"name", "description"));
        Course course = entityManager.find(Course.class, 1);
        student.setCoursesOfStud(new HashSet<>());
        course.setStudentSet(new HashSet<>());
        courseDao.setNewCourse(student.getStudentId(), course.getName());
        assertNotNull(student.getCoursesOfStud());
    }
    @Test
    void unlinkCourse_shouldRetrieveZeroRows(){
        entityManager.merge(new Student(1,1,"name", "surname"));
        Student student = entityManager.find(Student.class, 1);
        entityManager.merge(new Course(1,"name", "description"));
        Course course = entityManager.find(Course.class, 1);
        student.setCoursesOfStud(new HashSet<>());
        student.getCoursesOfStud().add(course);
        course.setStudentSet(new HashSet<>());
        course.getStudentSet().add(student);
        courseDao.unlinkCourse(student.getStudentId(),course.getName());
        assertEquals(0,student.getCoursesOfStud().size());
    }
    @Test
    void getCourseMembers_shouldRetrieveAllTheseStudents(){

        entityManager.merge(new Student(1,0,"a","a"));
        entityManager.merge(new Student(2,0,"b","b"));
        Student abc = entityManager.find(Student.class, 1);
        Student abc1 = entityManager.find(Student.class, 2);

        entityManager.merge(new Course(1, "mathematics", "description"));
        Course course1 = entityManager.find(Course.class, 1);

        abc.setCoursesOfStud(new HashSet<>());
        abc.getCoursesOfStud().add(course1);
        abc1.setCoursesOfStud(new HashSet<>());
        abc1.getCoursesOfStud().add(course1);
        course1.setStudentSet(new HashSet<>());
        course1.getStudentSet().add(abc);
        course1.getStudentSet().add(abc1);

        assertEquals(2, courseDao.getCourseMembers(course1.getName()).size());
    }

    @Test
    void findAvailableCourses_shouldRetrieveOneRow(){

        int studentID = 1;
        Student student = new Student(studentID,1,"a", "a");
        entityManager.merge(student);
        entityManager.merge(new Course(1, "algb", "description"));
        entityManager.merge(new Course(2, "algb2", "descriptio2"));


        Student abc = entityManager.find(Student.class, student.getStudentId());
        Course cs = entityManager.find(Course.class, 1);
        Course c2 = entityManager.find(Course.class, 2);
        List<Course> courses = new ArrayList<>(Arrays.asList(cs, c2));

        abc.setCoursesOfStud(new HashSet<>());
        abc.getCoursesOfStud().add(cs);
        cs.setStudentSet(new HashSet<>());
        cs.getStudentSet().add(abc);
        assertEquals(courses.size() - 1, courseDao.findAvailableCourses(studentID).size());

    }
}
