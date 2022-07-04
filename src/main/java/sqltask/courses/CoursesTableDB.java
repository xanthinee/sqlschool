package sqltask.courses;

import sqltask.applicationmenu.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoursesTableDB {

    Scanner sc = new Scanner(System.in);
    MethodsForCourses coursesMethods = new MethodsForCourses();

    public void putCoursesInTable(Connection con, String nameOfCoursesFile) throws SQLException{

        MethodsForCourses coursesMethods = new MethodsForCourses();
        List<Course> courses = coursesMethods.makeCoursesList(nameOfCoursesFile);
        try (PreparedStatement st = con.prepareStatement("insert into public.courses values (default,?,?)")) {
            con.setAutoCommit(false);
            for (Course course : courses) {
                st.setString(1, course.getName());
                st.setString(2, course.getDescription());
                st.addBatch();
            }
            st.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteCoursesFromTable(Connection con) {

        try (Connection connection = con;
             PreparedStatement st = connection.prepareStatement("delete from courses")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCoursesFromTable(Connection con) {

        List<Course> courses = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("select course_id, course_name, course_description FROM public.courses");
            ResultSet coursesRS = preparedStatement.executeQuery();
            while (coursesRS.next()) {
                courses.add(new Course(coursesRS.getInt("course_id"), coursesRS.getString("course_name"),
                        coursesRS.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<String> getCoursesOfStudent(Connection con, int studentID) {

        List<String> coursesOfStudent = new ArrayList<>();
        try {
            PreparedStatement getCoursesOfStud = con.prepareStatement("select course_name from courses c inner join students_courses sc " +
                    "on c.course_id = sc.course_id where student_id = ?");
            getCoursesOfStud.setInt(1, studentID);
            ResultSet coursesOfStud = getCoursesOfStud.executeQuery();
            while (coursesOfStud.next()) {
                coursesOfStudent.add(coursesOfStud.getString("course_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesOfStudent;
    }

    public String setNewCourse(Connection con) {

        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();

        List<String> availableCourses = new ArrayList<>();
        try {
            List<String> coursesOfStudent = getCoursesOfStudent(con, studentID);
            PreparedStatement avbCourses = con.prepareStatement("select c.course_name from courses c where c.course_id not in " +
                    "(select sc.course_id from students_courses sc where sc.student_id = ?)");
            avbCourses.setInt(1, studentID);
            ResultSet avbCoursesRs = avbCourses.executeQuery();
            while (avbCoursesRs.next()) {
                availableCourses.add(avbCoursesRs.getString("course_name"));
            }

            System.out.println(coursesMethods.infoToPrint(coursesOfStudent, availableCourses));
            System.out.println("Enter NAME (Only 1 by attempt) of COURSE which you want to ADD: ");
            String courseName = sc.next();
            PreparedStatement addCourseToStudent = con.prepareStatement("insert into students_courses (student_id, course_id) " +
                    "select ?, course_id from courses where course_name = ?");
            addCourseToStudent.setInt(1, studentID);
            addCourseToStudent.setString(2, courseName);
            addCourseToStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Course was added!";
    }
}
