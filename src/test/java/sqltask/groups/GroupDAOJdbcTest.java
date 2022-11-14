package sqltask.groups;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sqltask.JdbcDaoTestConfig;
import sqltask.students.Student;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(initializers = {GroupDAOJdbcTest.Initializer.class})
@Import(JdbcDaoTestConfig.class)
@SpringBootTest
@Testcontainers
class GroupDAOJdbcTest {

    @Autowired
    ApplicationContext ctx;

    @Autowired
    GroupDaoJdbc dao;
    @Autowired
    JdbcTemplate jdbc;

    private static final String GROUPS_TABLE = "groups";
    @Container
    static public PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("jdbc:tc:postgresql://localhost:5432/postgreSQLTaskFoxmindedTests")
            .withUsername("postgres")
            .withPassword("7777");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeEach
    public void clearContainer() {
        dao.deleteAll();
    }

    @Test
    void save_shouldRetrieveOnlyOneLine() {
        dao.save(new Group(1,"aa-11"));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, GROUPS_TABLE));
    }

    @Test
    void saveAll_shouldRetrieveSeveralRows() {

        List<Group> groups = new ArrayList<>(Arrays.asList(
                new Group(1,"aa-11"),
                new Group(2, "bb-22"),
                new Group(3,"cc-33")
        ));
        dao.saveAll(groups);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbc, GROUPS_TABLE));
    }

    @Test
    void deleteAll_shouldRetrieveZeroRows() {

        List<Group> groups = new ArrayList<>(Arrays.asList(
                new Group(1,"aa-11"),
                new Group(2, "bb-22"),
                new Group(3,"cc-33")
        ));

        jdbc.batchUpdate("insert into groups values(default,?)", groups, groups.size(),
                (PreparedStatement ps, Group group) -> ps.setString(1, group.getName()));
        dao.deleteAll();
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, GROUPS_TABLE));
    }

    @Test
    void getAll_sizesShouldBeEqual() {

        List<Group> groups = new ArrayList<>(Arrays.asList(
                new Group(1,"aa-11"),
                new Group(2, "bb-22"),
                new Group(3,"cc-33")
        ));

        jdbc.batchUpdate("insert into groups values(default,?)", groups, groups.size(),
                (PreparedStatement ps, Group group) -> ps.setString(1, group.getName()));
        assertEquals(groups.size(), dao.getAll().size());
    }

    @Test
    void getById_shouldRetrieveExactGroup() {

        int groupId = 100;
        Group group = new Group(groupId, "aa-11");
        jdbc.update("insert into groups values(?,?)", group.getId(), group.getName());
        assertEquals(group, dao.getById(100));
    }

    @Test
    void deleteById_shouldCountZeroRows() {

        int groupId = 100;
        Group group = new Group(groupId, "aa-11");
        jdbc.update("insert into groups values(?,?)", group.getId(), group.getName());
        dao.deleteById(groupId);
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, GROUPS_TABLE, "group_id = " + groupId));
    }

    @Test
    void compareGroups_shouldRetrieveThreeGroups() {

        List<Student> students = new ArrayList<>();
        Student[] studentsArray = {new Student(1, 1, "a", "a"),
                new Student(2, 2, "a", "a"),
                new Student(3, 2, "a", "a"),
                new Student(4, 3, "b", "b"),
                new Student(5, 3, "c", "c"),
                new Student(6, 3, "d", "d"),
                new Student(7, 4, "e", "e"),
                new Student(8, 4, "f", "f"),
                new Student(9, 4, "g", "g"),
                new Student(10, 4, "h", "h")};
        students.addAll(Arrays.asList(studentsArray));

        jdbc.batchUpdate("insert into students values(default,?,?,?)", students, students.size(),
                (PreparedStatement ps, Student student) -> {
                    ps.setInt(1, student.getGroupId());
                    ps.setString(2, student.getName());
                    ps.setString(3, student.getSurname());
                });

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1,"AA-11");
        Group group1 = new Group(2,"AB-12");
        Group group2 = new Group(3,"AC-13");
        Group group3 = new Group(4,"AD-14");
        groups.add(group);
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);

        jdbc.batchUpdate("insert into groups values(?,?)", groups, groups.size(),
                (PreparedStatement ps, Group groupJdbc) -> {
                    ps.setInt(1, groupJdbc.getId());
                    ps.setString(2, groupJdbc.getName());
                });

        assertEquals(3, dao.compareGroups(4).size());
    }
}
