package sqltask.studentscourses;

import sqltask.courses.Course;
import sqltask.courses.CourseMethods;
import sqltask.students.Student;
import sqltask.students.StudentsTableDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StudentsCoursesTableDB {

    private static final Set<Integer> usedIDs = new HashSet<>();
    Random rd = new Random();

    private int generateUniqueNum(int leftBound, int rightBound) {
        int num = rd.nextInt(leftBound, rightBound);
        if (usedIDs.contains(num)) {
            num = rd.nextInt(leftBound, rightBound);
        }
        usedIDs.add(num);
        return num;
    }

    public void createStdCrsTable(Connection con) throws SQLException {

        CourseMethods courseMtd = new CourseMethods();
        StudentsTableDB studentsDB = new StudentsTableDB();
        List<Course> courses = courseMtd.makeCoursesList("data/courses.txt");
        List<Student> students = studentsDB.getStudents(con);

        for (Student student : students) {
            int numOfCourses = rd.nextInt(1, 4);
            for (int i = 0; i < numOfCourses; i++) {
                try (PreparedStatement st = con.prepareStatement("insert into public.students_courses values (default,?,?)")) {
                    st.setInt(1, student.getStudentId());
                    st.setString(2, courses.get(generateUniqueNum(0, courses.size())).getName());
                    st.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteAllFromStudentsCourses(Connection con) throws SQLException {

        try (Connection connection = con;
             PreparedStatement st = connection.prepareStatement("delete from students_courses")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getRowsFromStudCourses(Connection con) throws SQLException {

        try (Connection connection = con;
             PreparedStatement st = connection.prepareStatement("select * from students_courses")){
            return st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("ResultSet wasn't created");
    }
}
