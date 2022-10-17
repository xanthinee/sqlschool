package sqltask.students;

import sqltask.connection.DataSource;
import sqltask.groups.GroupsTableDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("java:S106")
public class StudentsTableDB implements StudentDAO {

    private final String tableName;
    private final StudentMapper rowMapper = new StudentMapper();
    private final DataSource ds;
    Random rd = new Random();


    public StudentsTableDB(DataSource ds) {
        this.ds = ds;
        this.tableName = "students";
    }


    public List<Student> finishStudentsCreation() {

        MethodsForStudents studMethods = new MethodsForStudents();
        List<Student> students = studMethods.generateStudents();
        GroupsTableDB groupsTab = new GroupsTableDB(ds, "groups");
        List<Integer> iDs = groupsTab.groupsIdList();
        for (int id : iDs) {
            int groupMembers = rd.nextInt(0, 31);
            if (groupMembers >= 10) {
                for (int i = 0; i < groupMembers; i++) {
                    students.get(studMethods.generateUniqueNum(0, students.size())).setGroupId(id);
                }
            }
        }
        return students;
    }
    public void putStudentsIntoTable(List<Student> students) {
        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement("insert into public.students values (default,?,?,?)")){
            for (Student student : students) {
                if (student.getGroupId() == null) {
                    st.setNull(1, Types.NULL);
                } else {
                    st.setInt(1, student.getGroupId());
                }
                st.setString(2, student.getName());
                st.setString(3, student.getSurname());
                st.addBatch();
            }
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {

        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement("delete from students")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAll() {

        List<Student> students = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement psStudents = con.prepareStatement("select student_id, group_id, first_name, second_name" +
                     " from students")) {
            ResultSet rsStudents = psStudents.executeQuery();
            while (rsStudents.next()) {
                students.add(new Student(rsStudents.getInt("student_id"), rsStudents.getInt("group_id"),
                        rsStudents.getString("first_name"), rsStudents.getString("second_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void deleteById(int studentID) {

        try (Connection con = ds.getConnection();
             PreparedStatement deleteFromStudents = con.prepareStatement("delete from students where student_id = ?")) {
            deleteFromStudents.setInt(1, studentID);
            deleteFromStudents.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewStudent(String studentName, String studentSurname, int groupId) {

        try (Connection con = ds.getConnection();
             PreparedStatement putStudent = con.prepareStatement("insert into students values (default,?,?,?)")) {
            putStudent.setInt(1,groupId);
            putStudent.setString(2, studentName);
            putStudent.setString(3, studentSurname);
            putStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getById(int id) {

        try (Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from " + tableName + " WHERE student_id = ? ")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rowMapper.mapToEntity(rs);
            }
            throw new IllegalStateException("No data found for id " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}