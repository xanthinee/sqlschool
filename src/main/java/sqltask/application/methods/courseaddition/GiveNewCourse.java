package sqltask.application.methods.courseaddition;

import sqltask.courses.Course;
import sqltask.courses.CourseMethods;
import sqltask.application.methods.courseaddition.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings("java:S106")
public class GiveNewCourse {
    Scanner sc = new Scanner(System.in);
    GiveNewCourseMethods giveCourse = new GiveNewCourseMethods();

    public List<String> getCoursesOfStudent(Connection con, int studentID) {

        List<String> coursesOfStudent = new ArrayList<>();
        try (PreparedStatement getCoursesOfStud = con.prepareStatement("select course_name from students_courses where student_id = ?")) {
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

        CourseMethods courseMtd = new CourseMethods();

        System.out.println("Enter student_id of STUDENT: ");
        int studentID = sc.nextInt();
        List<Course> tempListOfAvailableCourses = courseMtd.makeCoursesList("data/courses.txt");

        Map<Integer, String> mainCourses = new HashMap<>();
        for (Course course : tempListOfAvailableCourses) {
            mainCourses.put(course.getId(), course.getName());
        }

            try {
                List<String> coursesOfStudent = getCoursesOfStudent(con, studentID);

                Map<Integer, String> availableForStudentCourse = giveCourse.findUnusedCourses(coursesOfStudent,mainCourses);
                int numOfCourses = coursesOfStudent.size();
                System.out.println(giveCourse.completeCoursesInfo(coursesOfStudent, mainCourses,availableForStudentCourse));

                if (numOfCourses < 3) {
                    System.out.println("Amount of new available COURSES is: " + (3 - numOfCourses));
                    System.out.println("Enter NAME (Only 1 by attempt) of COURSE which you want to ADD: ");
                } else {
                    throw new IllegalStateException("Chosen STUDENT already has maximum amount of COURSES, delete some before");
                }
                String courseName = sc.next();
                PreparedStatement addCourseToStudent = con.prepareStatement("insert into students_courses values (default,?,?)");
                addCourseToStudent.setInt(1, studentID);
                addCourseToStudent.setString(2, courseName);
                addCourseToStudent.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        }
    }

