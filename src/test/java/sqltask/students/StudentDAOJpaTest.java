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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(initializers = {ContainersConfig.Initializer.class})
@SpringBootTest(classes = ContainersConfig.class)
@ActiveProfiles(profiles = {"test", "jpa"})
public class StudentDAOJpaTest implements TestDAOInterface {

    @Autowired
    StudentDAOJpa studentDao;
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
        assertEquals(student, entityManager.find(Student.class, student.getStudentId()));
    }

    @Override
    @Test
    public void saveAll_shouldSaveSeveralRows() {

    }

    @Override
    @Test
    public void deleteAll_shouldRetrieveZeroRows() {

    }

    @Override
    @Test
    public void getAll_sizesShouldBeEqual() {

    }

    @Override
    @Test
    public void getById_shouldRetrieveExactEntity() {

    }

    @Override
    @Test
    public void deleteById_shouldCountZeroRows() {

    }
}
