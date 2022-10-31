package sqltask.groups;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqltask.connection.DataSource;
import sqltask.helpers.SQLScriptRunner;
import sqltask.students.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            sqlScriptRunner.executeScriptUsingScriptRunner("sqldata/tables_creation.sql", ds.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void save_whenThereGroupToSave_shouldRetrieveExactGroup() {

        int groupID = 1;
        Group group = new Group(groupID, "AA--11");
        Group groupToCompare = new Group(null, null);

        groupDAO.save(group);

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from groups")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                groupToCompare.setId(rs.getInt("group_id"));
                groupToCompare.setName(rs.getString("group_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(group, groupToCompare);
    }

    @Test
    void saveAll_whenThereGroupsToSave_shouldSaveAll() {

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1, "AA--11");
        Group group1 = new Group(2, "AB--12");
        Group group2 = new Group(3, "AC--13");
        groups.add(group);
        groups.add(group1);
        groups.add(group2);
        groupDAO.saveAll(groups);

        List<Group> retrievedGroups = new ArrayList<>();
        try (Connection connection = ds.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from groups")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrievedGroups.add(new Group(rs.getInt("group_id"), rs.getString("group_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(groups, retrievedGroups);
    }

    @Test
    void deleteAll_whenThereGroupsToDelete_shouldLeaveEmptyTable() {

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1, "AA--11");
        Group group1 = new Group(2, "AB--12");
        Group group2 = new Group(3, "AC--13");
        groups.add(group);
        groups.add(group1);
        groups.add(group2);

        try (Connection connection = ds.getConnection();
        PreparedStatement ps = connection.prepareStatement("insert into groups values(default, ?)")) {
            for (Group groupAdd : groups) {
                ps.setString(1, groupAdd.getName());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        groupDAO.deleteAll();

        List<Group> retrievedGroups = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from groups")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrievedGroups.add(new Group(rs.getInt("group_id"), rs.getString("group_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Group> emptyList = new ArrayList<>();
        assertEquals(emptyList, retrievedGroups);
    }

    @Test
    void getAll_whenThereSomeGroups_shouldRetrieveThemAll() {

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1, "AA--12");
        Group group1 = new Group(2, "AB--13");
        Group group2 = new Group(3, "AC--14");
        groups.add(group);
        groups.add(group1);
        groups.add(group2);

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into groups values(default, ?)")) {
            for (Group groupAdd : groups) {
                ps.setString(1, groupAdd.getName());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(groups, groupDAO.getAll());
    }

    @Test
    void getById_whenIDisDetermined_shouldRetrieveGroupWithSuchID() {

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1, "AA--12");
        Group group1 = new Group(2, "AB--13");
        Group group2 = new Group(3, "AC--14");
        groups.add(group);
        groups.add(group1);
        groups.add(group2);

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into groups values(default, ?)")) {
            for (Group groupAdd : groups) {
                ps.setString(1, groupAdd.getName());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(group1, groupDAO.getById(2));
    }

    @Test
    void deleteById_whenGroupWithSuchIdDeleted_shouldThrowISE() {

        int groupID = 1;
        Group group = new Group(groupID, "AA--11");

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into groups values(default, ?)")) {
                ps.setString(1, group.getName());
                ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        new Student(4, 3, "b", "b"),
        new Student(5, 3, "c", "c"),
        new Student(6, 3, "d", "d"),
        new Student(7, 4, "e", "e"),
        new Student(8, 4, "f", "f"),
        new Student(9, 4, "g", "g"),
        new Student(10, 4, "h", "h")};
        students.addAll(Arrays.asList(studentsArray));

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

        List<Group> groups = new ArrayList<>();
        Group group = new Group(1,"AA-11");
        Group group1 = new Group(2,"AB-12");
        Group group2 = new Group(3,"AC-13");
        Group group3 = new Group(4,"AD-14");
        groups.add(group);
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into groups values(default, ?)")) {
            for (Group groupAdd : groups) {
                ps.setString(1, groupAdd.getName());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }


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
