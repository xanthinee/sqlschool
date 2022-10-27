package sqltask.students;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import sqltask.connection.*;
import sqltask.helpers.SQLScriptRunner;

import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;

class StudentDAOImplTest {

    DataSource ds = new DataSource("testdata/connectiontests.properties");
    SQLScriptRunner sqlScriptRunner = new SQLScriptRunner();

    StudentDAO studentDAO = new StudentDAOImpl(ds);

    @BeforeEach
    public void init() {
        try {
            DataSource ds = new DataSource("testdata/connectiontests.properties");
            sqlScriptRunner.executeScriptUsingScriptRunner("sqltestdata/test_table_creation.sql", ds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void destroy() {
        try {
            DataSource ds = new DataSource("testdata/connectiontests.properties");
            sqlScriptRunner.executeScriptUsingScriptRunner("sqltestdata/test_table_delete.sql", ds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveAll_whenThereStudentsToSave_shouldSaveAll() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1, 0, "a", "a");
        Student student1 = new Student(2, 0, "a", "a");
        Student student2 = new Student(3, 0, "a", "a");
        students.add(student);
        students.add(student1);
        students.add(student2);

        studentDAO.saveAll(students);
        assertEquals(students, studentDAO.getAll());
    }

    @Test
    void deleteAll_whenThereStudentsToDelete_shouldLeaveEmptyTable() {

        List<Student> studentsToAdd = new ArrayList<>();
        List<Student> emptyList = new ArrayList<>();
        Student student = new Student(1, 0, "a", "a");
        Student student1 = new Student(2, 0, "a", "a");
        Student student2 = new Student(3, 0, "a", "a");

        studentsToAdd.add(student);
        studentsToAdd.add(student1);
        studentsToAdd.add(student2);

        studentDAO.saveAll(studentsToAdd);
        studentDAO.deleteAll();
        assertEquals(emptyList, studentDAO.getAll());
    }

    @Test
    void getAll_whenThereSomeStudents_shouldRetrieveThemAll() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1, 0, "a", "a");
        Student student1 = new Student(2, 0, "a", "a");
        Student student2 = new Student(3, 0, "a", "a");
        students.add(student);
        students.add(student1);
        students.add(student2);

        studentDAO.saveAll(students);
        assertEquals(students, studentDAO.getAll());
    }

    @Test
    void deleteById_whenStudentWithSuchIdDeleted_shouldThrowISE() {

        int studID = 1;
        Student student = new Student(studID,null,"a", "a");
        studentDAO.save(student);
        studentDAO.deleteById(studID);
        Assertions.assertThrows(IllegalStateException.class, ()-> {studentDAO.getById(studID);},
                "No data found for id " + studID);
    }

    @Test
    void save_whenThereStudentToSave_shouldRetrieveExactStudent() {

        int studID = 1;
        Student studentToSave = new Student(studID, null, "a", "a");
        Student studentToCompare = new Student(studID, 0, "a", "a");
        studentDAO.save(studentToSave);
        assertEquals(studentToCompare, studentDAO.getById(studID));
    }

    @Test
    void updateGroupIdByStudId_whenStudentAndGroupIdAreDetermined_shouldUpgradeGroupID() {

        Student student = new Student(1,null, "a", "a");
        studentDAO.save(student);
        studentDAO.updateGroupIdByStudId(student, 2);
        Student studentToCheckChanges = new Student(1,2,"a", "a");
        assertEquals(studentToCheckChanges, studentDAO.getById(1));
    }

    @Test
    void getById_whenIDisDetermined_shouldRetrieveStudentWithSuchID() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1, 0, "a", "a");
        Student student1 = new Student(2, 0, "b", "b");
        Student student2 = new Student(3, 0, "c", "c");
        students.add(student);
        students.add(student1);
        students.add(student2);

        studentDAO.saveAll(students);
        assertEquals(student1, studentDAO.getById(2));
    }
}
