package sqltask.students;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import sqltask.ContainersConfig;
import sqltask.TestDAOInterface;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(initializers = {ContainersConfig.Initializer.class})
@SpringBootTest(classes = ContainersConfig.class)
@ActiveProfiles(profiles = {"test", "jdbc"})
class StudentDAOJdbcTest implements TestDAOInterface {

    @Autowired
    ApplicationContext ctx;
    @Autowired
    StudentDAOJdbc dao;
    @Autowired
    JdbcTemplate jdbc;

    private static final String STUDENTS_TABLE = "students";

    @BeforeEach
    public void clearContainer() {
        JdbcTestUtils.deleteFromTables(jdbc, STUDENTS_TABLE);
    }

    @Override
    @Test
    public void save_shouldSaveOnlyOneLine() {
        dao.save(new Student(1,1,"a", "a"));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, STUDENTS_TABLE));
    }

    @Override
    @Test
    public void saveAll_shouldSaveSeveralRows() {

        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student(1, 1,"a", "a"),
                new Student(2,2, "b","b"),
                new Student(3,3,"c", "a")
        ));
        dao.saveAll(students);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbc, STUDENTS_TABLE));
    }

    @Override
    @Test
    public void deleteAll_shouldRetrieveZeroRows() {

        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student(1, 1,"a", "a"),
                new Student(2,2, "b","b"),
                new Student(3,3,"c", "a")
        ));
        jdbc.batchUpdate("insert into students values(default,default,?,?)", students, students.size(),
                (PreparedStatement ps, Student student) -> {
                    ps.setString(1, student.getName());
                    ps.setString(2, student.getSurname());
                });

        dao.deleteAll();
        assertEquals(0,JdbcTestUtils.countRowsInTable(jdbc, STUDENTS_TABLE));
    }

    @Override
    @Test
    public void getAll_sizesShouldBeEqual(){

        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student(1, 1,"a", "a"),
                new Student(2,2, "b","b"),
                new Student(3,3,"c", "a")
        ));
        jdbc.batchUpdate("insert into students values(default,default,?,?)", students, students.size(),
                (PreparedStatement ps, Student student) -> {
                    ps.setString(1, student.getName());
                    ps.setString(2, student.getSurname());
                });
        assertEquals(students.size(), dao.getAll().size());
    }

    @Override
    @Test
    public void getById_shouldRetrieveExactEntity() {

        int studentId = 100;
        Student student = new Student(studentId, 1, "a", "a");
        jdbc.update("insert into students values(?,?,?,?)", student.getStudentId(), student.getGroupId(),
                student.getName(), student.getSurname());
        assertEquals(student, dao.getById(100));
    }

    @Override
    @Test
    public void deleteById_shouldCountZeroRows() {

        int studentId = 1;
        jdbc.update("insert into " + STUDENTS_TABLE + " values(?, 10, 'a', 'a')", studentId);
        dao.deleteById(studentId);
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, STUDENTS_TABLE, "student_id =" + studentId));
    }

    @Test
    void updateGroupIdByStudId_shouldRetrieveOneRow() {

        int studentId = 100;
        Student student = new Student(studentId, 1, "a", "a");
        jdbc.update("insert into students values(?,?,?,?)", student.getStudentId(), student.getGroupId(),
                student.getName(), student.getSurname());
        int newGroupId = 5;
        dao.updateGroupIdByStudId(student, newGroupId);
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, STUDENTS_TABLE, "group_id =" + newGroupId));
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, STUDENTS_TABLE, "group_id = 1"));
    }
}
