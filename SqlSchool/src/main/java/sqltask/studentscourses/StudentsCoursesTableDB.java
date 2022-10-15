package sqltask.studentscourses;

import sqltask.connection.ConnectionProvider;
import sqltask.courses.Course;
import sqltask.courses.CoursesTableDB;
import sqltask.courses.MethodsForCourses;
import sqltask.students.Student;
import sqltask.students.StudentsTableDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings("java:S106")
public class StudentsCoursesTableDB {

    ConnectionProvider conProvider;
    Random rd = new Random();

    public StudentsCoursesTableDB(ConnectionProvider conProvider) {
        this.conProvider = conProvider;
    }

    public void createStdCrsTable() throws SQLException {

        StudentsTableDB studentsDB = new StudentsTableDB(conProvider);
        CoursesTableDB coursesDb = new CoursesTableDB(conProvider);
        List<Student> students = studentsDB.getStudents();
        List<Course> courses = coursesDb.getCoursesFromTable();

        for (Student student : students) {
            int numOfCourses = rd.nextInt(1, 4);
                try (Connection con = conProvider.getConnection();
                        PreparedStatement st = con.prepareStatement("insert into public.students_courses values (default,?,?)")) {
                    for (int i = 0; i < numOfCourses; i++ ) {
                        st.setInt(1, student.getStudentId());
                        st.setInt(2, courses.get(rd.nextInt(0, courses.size())).getId());
                        st.addBatch();
                    }
                    st.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public void deleteAllFromStudentsCourses() throws SQLException {

        try (Connection con = conProvider.getConnection();
             PreparedStatement st = con.prepareStatement("delete from students_courses")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<StudentCourse> getStudCourses() throws SQLException {

        List<StudentCourse> stdCrs = new ArrayList<>();
        try (Connection con = conProvider.getConnection();
             PreparedStatement st = con.prepareStatement("select * from students_courses")){
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

    public List<Student> getCourseMembers(String courseName) {

        List<Student> students = new ArrayList<>();
        try (Connection con = conProvider.getConnection();
             PreparedStatement st = con.prepareStatement("select s.student_id, s.group_id, s.first_name, s.second_name \n" +
                     "from students s " +
                     "inner join students_courses sc on sc.student_id = s.student_id " +
                     "inner join courses c on sc.course_id = c.course_id " +
                     "where c.course_name = ?")) {
            st.setString(1, courseName);
            ResultSet studentsRS = st.executeQuery();
            while (studentsRS.next()) {
                students.add(new Student(studentsRS.getInt("student_id"), studentsRS.getInt("group_id"),
                        studentsRS.getString("first_name"), studentsRS.getString("second_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public String unlinkCourse(int studentID, String courseToDelete) {

        try (Connection con = conProvider.getConnection();
             PreparedStatement unlinkCourse = con.prepareStatement("delete from students_courses sc " +
                     "using courses c " +
                     "where c.course_id = sc.course_id and " +
                     "sc.student_id = ? and c.course_name = ?")) {
            unlinkCourse.setInt(1, studentID);
            unlinkCourse.setString(2, courseToDelete);
            unlinkCourse.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "COURSE WAS UNLINKED";
    }
}
