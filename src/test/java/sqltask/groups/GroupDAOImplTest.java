package sqltask.groups;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqltask.connection.DataSource;
import sqltask.helpers.SQLScriptRunner;
import sqltask.students.Student;
import sqltask.students.StudentDAO;
import sqltask.students.StudentDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;


import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupDAOImplTest {

    DataSource ds = new DataSource("testdata/connectiontests.properties");
    SQLScriptRunner sqlScriptRunner = new SQLScriptRunner();
    GroupDAO groupDAO = new GroupDAOImpl(ds);

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
    void save_whenThereGroupToSave_shouldRetrieveExactGroup() {

        int groupID = 1;
        Group group = new Group(groupID, "AA--11");
        groupDAO.save(group);
        assertEquals(group, groupDAO.getById(groupID));
    }

    @Test
    void saveAll_whenThereGroupsToSave_shouldSaveAll() {

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1, "AA--11");
        Group group1 = new Group(2, "AA--11");
        Group group2 = new Group(3, "AA--11");

        groups.add(group);
        groups.add(group1);
        groups.add(group2);

        groupDAO.saveAll(groups);
        assertEquals(groups, groupDAO.getAll());
    }

    @Test
    void deleteAll_whenThereGroupsToDelete_shouldLeaveEmptyTable() {

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1, "AA--11");
        Group group1 = new Group(2, "AA--11");
        Group group2 = new Group(3, "AA--11");

        groups.add(group);
        groups.add(group1);
        groups.add(group2);

        groupDAO.saveAll(groups);
        groupDAO.deleteAll();
        List<Group> emptyList = new ArrayList<>();
        assertEquals(emptyList, groupDAO.getAll());
    }

    @Test
    void getAll_whenThereSomeGroups_shouldRetrieveThemAll() {

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1, "AA--11");
        Group group1 = new Group(2, "AA--11");
        Group group2 = new Group(3, "AA--11");

        groups.add(group);
        groups.add(group1);
        groups.add(group2);

        groupDAO.saveAll(groups);
        assertEquals(groups, groupDAO.getAll());
    }

    @Test
    void getById_whenIDisDetermined_shouldRetrieveGroupWithSuchID() {

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1, "AA--11");
        Group group1 = new Group(2, "AA--11");
        Group group2 = new Group(3, "AA--11");

        groups.add(group);
        groups.add(group1);
        groups.add(group2);

        groupDAO.saveAll(groups);
        assertEquals(group1, groupDAO.getById(2));
    }

    @Test
    void deleteById_whenGroupWithSuchIdDeleted_shouldThrowISE() {

        int groupID = 1;
        Group group = new Group(groupID, "AA--11");
        groupDAO.save(group);
        groupDAO.deleteById(groupID);
        Assertions.assertThrows(IllegalStateException.class, ()-> {groupDAO.getById(groupID);},
                "No data found for id " + groupID);
    }

    @Test
    void compareGroup_whenThereGroupsToCompare_shouldReturnGroupsWithLessOrEqualNumberOfMembers() {

        List<Student> students = new ArrayList<>();
        Student[] studentsArray = {new Student(1, 1, "a", "a"),
                new Student(2, 2, "a", "a"),
        new Student(3, 2, "a", "a"),
        new Student(4, 3, "a", "a"),
        new Student(5, 3, "a", "a"),
        new Student(6, 3, "a", "a"),
        new Student(7, 4, "a", "a"),
        new Student(8, 4, "a", "a"),
        new Student(9, 4, "a", "a"),
        new Student(10, 4, "a", "a")};
        students.addAll(Arrays.asList(studentsArray));

        StudentDAO studentDAO = new StudentDAOImpl(ds);
        studentDAO.saveAll(students);

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1,"AA-11");
        Group group1 = new Group(2,"AB-12");
        Group group2 = new Group(3,"AC-13");
        Group group3 = new Group(4,"AD-14");
        groups.add(group);
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
        groupDAO.saveAll(groups);

        List<Group> groupsResult = new ArrayList<>();
        groupsResult.add(group);
        groupsResult.add(group1);
        groupsResult.add(group2);
        Set<Group> setToCheck = new HashSet<>(groupsResult);
        setToCheck.addAll(groupsResult);
        Set<Group> retrievedSet = new HashSet<>(groupsResult);
        retrievedSet.addAll(groupDAO.compareGroups(4));
        assertEquals(setToCheck, retrievedSet);
    }
}
