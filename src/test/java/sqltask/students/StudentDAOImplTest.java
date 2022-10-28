package sqltask.students;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import sqltask.connection.*;
import sqltask.helpers.SQLScriptRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            sqlScriptRunner.executeScriptUsingScriptRunner("sqltestdata/test_table_creation.sql", ds.getConnection());
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

        List<Student> retrievedStudents = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select * " +
                     " from students")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrievedStudents.add(new Student(rs.getInt("student_id"), rs.getInt("group_id"),
                        rs.getString("first_name"), rs.getString("second_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(students, retrievedStudents);
    }

    @Test
    void deleteAll_whenThereStudentsToDelete_shouldRetrieveEmptyList() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1, 1, "a", "a");
        Student student1 = new Student(2, 1, "a", "a");
        Student student2 = new Student(3, 1, "a", "a");
        students.add(student);
        students.add(student1);
        students.add(student2);

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into students values (default,1,?,?)")) {
            for (Student stud : students) {
                ps.setString(1, stud.getName());
                ps.setString(2, stud.getSurname());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        studentDAO.deleteAll();

        List<Student> retrievedStudents = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select * " +
                     " from students")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrievedStudents.add(new Student(rs.getInt("student_id"), rs.getInt("group_id"),
                        rs.getString("first_name"), rs.getString("second_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Student> emptyList = new ArrayList<>();
        assertEquals(emptyList, retrievedStudents);
    }

    @Test
    void getAll_whenThereSomeStudents_shouldRetrieveThemAll() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1, 1, "a", "a");
        Student student1 = new Student(2, 1, "a", "a");
        Student student2 = new Student(3, 1, "a", "a");
        students.add(student);
        students.add(student1);
        students.add(student2);

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into students values (default,1,?,?)")) {
            for (Student stud : students) {
                ps.setString(1, stud.getName());
                ps.setString(2, stud.getSurname());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(students, studentDAO.getAll());
    }

    @Test
    void deleteById_whenStudentWithSuchIdDeleted_shouldThrowISE() {

        int studID = 1;
        Student student = new Student(studID,null,"a", "a");

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into students values (default,null,?,?)")) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getSurname());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        studentDAO.deleteById(studID);
        Assertions.assertThrows(IllegalStateException.class, ()-> {studentDAO.getById(studID);},
                "No data found for id " + studID);
    }

    @Test
    void save_whenThereStudentToSave_shouldRetrieveExactStudent() {

        int studID = 1;
        Student studentToSave = new Student(studID, 0, "a", "a");
        Student studentToCompare = new Student(null, null, null, null);
        studentDAO.save(studentToSave);

        try (Connection connection = ds.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from students")) {
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                studentToCompare.setStudentId(rs.getInt("student_id"));
                studentToCompare.setGroupId(rs.getInt("group_id"));
                studentToCompare.setName(rs.getString("first_name"));
                studentToCompare.setSurname(rs.getString("second_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(studentToSave, studentToCompare);
    }

    @Test
    void updateGroupIdByStudId_whenStudentAndGroupIdAreDetermined_shouldUpgradeGroupID() {

        int idOfModifiedStudent = 1;
        Student student = new Student(idOfModifiedStudent,null, "a", "a");

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into students values (default,null,?,?)")) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getSurname());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        studentDAO.updateGroupIdByStudId(student, 2);
        Student studentToCheckChanges = new Student(1,2,"a", "a");

        Student upgradedStudent = new Student(null, null, null, null);
        try (Connection connection = ds.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from students where student_id = " + idOfModifiedStudent)){
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                upgradedStudent.setStudentId(rs.getInt("student_id"));
                upgradedStudent.setGroupId(rs.getInt("group_id"));
                upgradedStudent.setName(rs.getString("first_name"));
                upgradedStudent.setSurname(rs.getString("second_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(studentToCheckChanges, upgradedStudent);
    }

    @Test
    void getById_whenIDisDetermined_shouldRetrieveStudentWithSuchID() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1, 1, "a", "a");
        Student student1 = new Student(2, 1, "b", "b");
        Student student2 = new Student(3, 1, "c", "c");
        students.add(student);
        students.add(student1);
        students.add(student2);

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into students values (default,1,?,?)")) {
            for (Student stud : students) {
                ps.setString(1, stud.getName());
                ps.setString(2, stud.getSurname());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(student1, studentDAO.getById(2));
    }
}
