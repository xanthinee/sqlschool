package sqltask.students;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import sqltask.ContainersConfig;
import sqltask.TestDAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ContextConfiguration(initializers = {ContainersConfig.Initializer.class})
@SpringBootTest(classes = ContainersConfig.class)
@ActiveProfiles(profiles = {"test", "jpa"})
public class StudentDAOJpaTest implements TestDAOInterface {

    @Autowired
    StudentDAOJpa studentDao;
    @PersistenceContext
    @Autowired
    EntityManager entityManager;
    @Autowired
    JdbcTemplate jdbc;

    @BeforeEach
    public void clearContainer() {
        JdbcTestUtils.deleteFromTables(jdbc, "students");
    }
    @Override
    @Test
    public void save_shouldSaveOnlyOneLine() {
        Student student = new Student(1,1,"name","surname");
        studentDao.save(student);
        assertNotNull(entityManager.find(Student.class, student.getStudentId()));
    }

    @Override
    @Test
    public void saveAll_shouldSaveSeveralRows() {
        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student(1,1,"name1","surname1"),
                new Student(2,2,"name2","surname2"),
                new Student(3,3,"name3","surname3")
        ));
        studentDao.saveAll(students);
        assertEquals(3, entityManager.createQuery("select s from student s").getResultList().size());
    }

    @Override
    @Test
    public void deleteAll_shouldRetrieveZeroRows() {
        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student(1,1,"name1","surname1"),
                new Student(2,2,"name2","surname2"),
                new Student(3,3,"name3","surname3")
        ));
        for (Student student : students) {
            entityManager.persist(student);
        }
        studentDao.deleteAll();
        assertEquals(0, entityManager.createQuery("select s from student s").getResultList().size());
    }

    @Override
    @Test
    public void getAll_sizesShouldBeEqual() {
        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student(1,1,"name1","surname1"),
                new Student(2,2,"name2","surname2"),
                new Student(3,3,"name3","surname3")
        ));
        for (Student student : students) {
            entityManager.persist(student);
        }
        assertEquals(students.size(), studentDao.getAll().size());
    }

    @Override
    @Test
    public void getById_shouldRetrieveExactEntity() {
        Student student = new Student(1,1,"name", "surname");
        entityManager.persist(student);
        assertEquals(student, studentDao.getById(student.getStudentId()));
    }

    @Override
    @Test
    public void deleteById_shouldCountZeroRows() {
        Student student = new Student(1,1,"name", "surname");
        entityManager.persist(student);
        studentDao.deleteById(student.getStudentId());
        assertNull(entityManager.find(Student.class, student.getStudentId()));
    }

    @Test
    void updateGroupIdByStudId_shouldRetrieveOneRow() {
        Student student = new Student(1,1,"name", "surname");
        entityManager.persist(student);
        int newGroupID = 9;
        studentDao.updateGroupIdByStudId(student, newGroupID);
        assertEquals(newGroupID, entityManager.find(Student.class, student.getStudentId()).getGroupId());
    }
}
