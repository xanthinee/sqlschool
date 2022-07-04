package sqltask.studentscourses;

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

    Random rd = new Random();
    Scanner sc = new Scanner(System.in);
    MethodsForStudCourses stdCrsMethods = new MethodsForStudCourses();
    CoursesTableDB courseDB = new CoursesTableDB();
    MethodsForCourses coursesMethods = new MethodsForCourses();

    public void createStdCrsTable(Connection con) throws SQLException {

        StudentsTableDB studentsDB = new StudentsTableDB();
        CoursesTableDB coursesDb = new CoursesTableDB();
        List<Student> students = studentsDB.getStudents(con);
        List<Course> courses = coursesDb.getCoursesFromTable(con);

        for (Student student : students) {
            int numOfCourses = rd.nextInt(1, 4);
                try (PreparedStatement st = con.prepareStatement("insert into public.students_courses values (default,?,?)")) {
                    con.setAutoCommit(false);
                    for (int i = 0; i < numOfCourses; i++ ) {
                        st.setInt(1, student.getStudentId());
                        st.setInt(2, courses.get(rd.nextInt(0, courses.size())).getId());
                        st.addBatch();
                    }
                    st.executeUpdate();
                    con.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
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

    public String getCourseMembers(Connection con) {

        System.out.println("ENTER name of COURSE bellow: ");
        String courseName = sc.next();
        List<Student> students = new ArrayList<>();
        try (Connection connection = con;
             PreparedStatement st = connection.prepareStatement("select s.student_id, s.group_id, s.first_name, s.second_name \n" +
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
        return stdCrsMethods.printMembers(students);
    }

    public String unlinkCourse(Connection con) {

        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        List<String> coursesOfStudent = courseDB.getCoursesOfStudent(con, studentID);
        System.out.println(coursesMethods.printCoursesOfStud(coursesOfStudent));
        System.out.println("You can DELETE one of them - ENTER bellow it's NAME: ");
        String courseToDelete = sc.next();
        try (Connection connection = con;
             PreparedStatement unlinkCourse = connection.prepareStatement("delete from students_courses sc " +
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
