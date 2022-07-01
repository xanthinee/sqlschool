package sqltask.application.methods;

import sqltask.connection.ConnectionInfoGenerator;
import sqltask.connection.UserConnection;
import sqltask.courses.Course;
import sqltask.courses.CourseMethods;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings("java:S106")
public class GiveNewCourseToStudent {
    Scanner sc = new Scanner(System.in);
    Random rd = new Random();

    private int generateNewId(int leftBound, int rightBound, List<Integer> used) {
        int newId = rd.nextInt(leftBound, rightBound);
        if (used.contains(newId)) {
            newId = rd.nextInt(leftBound, rightBound);
        }
        used.add(newId);
        return newId;
    }
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
    public String printCoursesOfStud(List<String> courses) {
        StringJoiner sj = new StringJoiner("");
        sj.add("Entered STUDENT has next COURSES: " + System.lineSeparator());
        int index = 1;
        for (String course : courses) {
            sj.add(index + ". " + course);
            sj.add(System.lineSeparator());
            index++;
        }
        return sj.toString();
    }

    private Map<Integer, String> findUnusedCourses(List<String> coursesOfStudent, Map<Integer, String> availableCourses) {

        for (Map.Entry<Integer, String> entry : new HashSet<>(availableCourses.entrySet())) {
            for (String course : coursesOfStudent) {
                if (entry.getValue().trim().equals(course.trim())) {
                    availableCourses.remove(entry.getKey());
                }
            }
        }
        return availableCourses;
    }

    private String completeCoursesInfo(List<String> coursesOfStudent, Map<Integer, String> mainCourses,
                                       Map<Integer, String> availableForStudentCourses) {
        int numOfCourses = coursesOfStudent.size();
        StringJoiner sj = new StringJoiner("");
        if (numOfCourses != 0) {
            sj.add(printCoursesOfStud(coursesOfStudent));
            sj.add(System.lineSeparator());
            sj.add("You can give next COURSES to STUDENT:" + System.lineSeparator());
            int index = 1;
            for (Map.Entry<Integer, String> entry : availableForStudentCourses.entrySet()) {
                sj.add(index + ". " + entry.getValue() + System.lineSeparator());
                index++;
            }
        } else {
            sj.add("Chosen STUDENT has no any COURSES");
            sj.add("You can give next COURSES to STUDENT:");
            int index = 1;
            for (Map.Entry<Integer,String> entry : mainCourses.entrySet()) {
                sj.add(index + ". " + entry.getValue() + System.lineSeparator());
                index++;
            }
            return sj.toString();
        }
        return sj.toString();
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

                Map<Integer, String> availableForStudentCourse = findUnusedCourses(coursesOfStudent,mainCourses);
                int numOfCourses = coursesOfStudent.size();
                System.out.println(completeCoursesInfo(coursesOfStudent, mainCourses,availableForStudentCourse));

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

