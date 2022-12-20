package sqltask.groups;

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
import sqltask.students.Student;

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
public class GroupDAOJpaTest implements TestDAOInterface {

    @Autowired
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    GroupDAOJpa groupDao;
    @Autowired
    JdbcTemplate jdbc;
    @BeforeEach
    public void clearContainer() {
        JdbcTestUtils.deleteFromTables(jdbc, "groups");
    }
    @Override
    @Test
    public void save_shouldSaveOnlyOneLine() {
        Group group = new Group(1, "name");
        groupDao.save(group);
        assertNotNull(entityManager.find(Group.class, group.getId()));
    }

    @Override
    @Test
    public void saveAll_shouldSaveSeveralRows() {
        List<Group> groups = new ArrayList<>(Arrays.asList(
                new Group(1, "name1"),
                new Group(2, "name2"),
                new Group(3, "name3")
        ));
        groupDao.saveAll(groups);
        assertEquals(3, entityManager.createQuery("select g from Group g").getResultList().size());
    }

    @Override
    @Test
    public void deleteAll_shouldRetrieveZeroRows() {
        List<Group> groups = new ArrayList<>(Arrays.asList(
                new Group(1, "name1"),
                new Group(2, "name2"),
                new Group(3, "name3")
        ));
        for (Group group : groups) {
            entityManager.persist(group);
        }
        groupDao.deleteAll();
        assertEquals(0, entityManager.createQuery("select g from Group g").getResultList().size());
    }

    @Override
    @Test
    public void getAll_sizesShouldBeEqual() {
        List<Group> groups = new ArrayList<>(Arrays.asList(
                new Group(1, "name1"),
                new Group(2, "name2"),
                new Group(3, "name3")
        ));
        for (Group group : groups) {
            entityManager.persist(group);
        }
        assertEquals(groups.size(), groupDao.getAll().size());
    }

    @Override
    @Test
    public void getById_shouldRetrieveExactEntity() {
        Group group = new Group(1, "name");
        entityManager.persist(group);
        assertEquals(group, groupDao.getById(group.getId()));
    }

    @Override
    @Test
    public void deleteById_shouldCountZeroRows() {
        Group group = new Group(1, "name");
        entityManager.persist(group);
        groupDao.deleteById(group.getId());
        assertNull(entityManager.find(Group.class, group.getId()));
    }

    @Test
    void compareGroups_shouldRetrieveThreeGroups() {

        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student(1, 1, "a", "a"),
                new Student(2, 2, "a", "a"),
                new Student(3, 2, "a", "a"),
                new Student(4, 3, "b", "b"),
                new Student(5, 3, "c", "c"),
                new Student(6, 3, "d", "d"),
                new Student(7, 4, "e", "e"),
                new Student(8, 4, "f", "f"),
                new Student(9, 4, "g", "g"),
                new Student(10, 4, "h", "h")
        ));
        for (Student student : students) {
            entityManager.persist(student);
        }

        List<Group> groups = new ArrayList<>(Arrays.asList(
                new Group(1, "name1"),
                new Group(2, "name2"),
                new Group(3, "name3"),
                new Group(4, "name4")
        ));
        for (Group group : groups) {
            entityManager.persist(group);
        }

        assertEquals(3, groupDao.compareGroups(4).size());
    }
}
