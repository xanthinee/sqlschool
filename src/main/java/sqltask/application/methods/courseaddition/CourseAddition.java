package sqltask.application.methods.courseaddition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings("java:S106")
public class CourseAddition {
    Scanner sc = new Scanner(System.in);
    CourseAdditionMethods giveCourse = new CourseAdditionMethods();

    public List<String> getCoursesOfStudent(Connection con, int studentID) {

        List<String> coursesOfStudent = new ArrayList<>();
        try {
            PreparedStatement getCoursesOfStud = con.prepareStatement("select course_name from courses c inner join students_courses sc " +
                    "on c.course_id = sc.course_id where student_id = ?");
            getCoursesOfStud.setInt(1, studentID);
            ResultSet coursesOfStud = getCoursesOfStud.executeQuery();
            while (coursesOfStud.next()) {
                coursesOfStudent.add(coursesOfStud.getString("course_name").trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesOfStudent;
    }

    public void giveCourseToStudent(Connection con) throws SQLException {

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

                System.out.println(giveCourse.infoToPrint(coursesOfStudent, availableCourses));
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
        }
    }

