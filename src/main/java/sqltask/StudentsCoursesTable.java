package sqltask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import sqltask.students.*;
import sqltask.courses.*;
import sqltask.connection.*;

import java.sql.SQLException;

public class StudentsCoursesTable {

    private final ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    private static final Set<Integer> usedIDs = new HashSet<>();
    Random rd = new Random();
    private static final String CONNECTION_FILE = "data/connectioninfo";

    private int generateUniqueNum(int leftBound, int rightBound) {
        int num = rd.nextInt(leftBound, rightBound);
        if (usedIDs.contains(num)) {
            num = rd.nextInt(leftBound, rightBound);
        }
        usedIDs.add(num);
        return num;
    }

    public void createStdCrsTable() throws SQLException {

        StudentsTableDB studentsDB = new StudentsTableDB();
        List<Student> students = studentsDB.getStudents();
        CourseMethods courseMtd = new CourseMethods();
        List<Course> courses = courseMtd.makeCoursesList("data/descriptions");

        for (Student student : students) {
            int numOfCourses = rd.nextInt(1, 4);
            for (int i = 0; i < numOfCourses; i++) {
                try (Connection connection = conInfo.getConnection(CONNECTION_FILE);
                     PreparedStatement st = connection.prepareStatement("insert into public.students_courses values (?,?,?)")) {
                    st.setInt(1, generateUniqueNum(1000, 10000));
                    st.setInt(2, student.getStudentId());
                    st.setString(3, courses.get(generateUniqueNum(0, courses.size())).getName());
                    st.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteAllFromStudentsCourses() throws SQLException {

        try (Connection connection = conInfo.getConnection(CONNECTION_FILE);
             PreparedStatement st = connection.prepareStatement("delete from students_courses")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getRowsFromStudCourses() throws SQLException {

        try (Connection connection = conInfo.getConnection(CONNECTION_FILE);
             PreparedStatement st = connection.prepareStatement("select * from students_courses")){
            return st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("ResultSet wasn't created");
    }

    public String printStudCourseTable() throws SQLException {

        ResultSet rs = getRowsFromStudCourses();
        StringJoiner sj = new StringJoiner("");
        sj.add("STUDENTS's COURSES");
        sj.add(System.lineSeparator());
        while (rs.next()) {
            sj.add(rs.getInt("student_id") + " | ");
            sj.add(String.format("%-20s", rs.getString("course_name").trim()) + "  | ");
            if (!rs.isLast()) {
                sj.add(System.lineSeparator());
            }
        }
        return sj.toString();
    }
}
