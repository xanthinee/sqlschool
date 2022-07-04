package sqltask.studentscourses;

import sqltask.courses.Course;
import sqltask.courses.CoursesTableDB;
import sqltask.students.Student;
import sqltask.students.StudentsTableDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StudentsCoursesTableDB {

    Random rd = new Random();

    public void createStdCrsTable(Connection con) throws SQLException {

        StudentsTableDB studentsDB = new StudentsTableDB();
        CoursesTableDB coursesDb = new CoursesTableDB();
        List<Student> students = studentsDB.getStudents(con);
        List<Course> courses = coursesDb.getCoursesFromTable(con);

        for (Student student : students) {
            int numOfCourses = rd.nextInt(1, 4);
            for (int i = 0; i < numOfCourses; i++) {
                try (PreparedStatement st = con.prepareStatement("insert into public.students_courses values (default,?,?)")) {
                    st.setInt(1, student.getStudentId());
                    st.setInt(2, courses.get(rd.nextInt(0, courses.size())).getId());
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

    private List<StudentCourse> getStudCourses(Connection con) throws SQLException {

        List<StudentCourse> stdCrs = new ArrayList<>();
        try (Connection connection = con;
             PreparedStatement st = connection.prepareStatement("select * from students_courses")){
            ResultSet tableValues = st.executeQuery();
            while (tableValues.next()) {
                stdCrs.add(new StudentCourse(tableValues.getInt("row_id"), tableValues.getInt("student_id"),
                        tableValues.getInt("course_id")));
            }
            return stdCrs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("ResultSet wasn't created");
    }
}
