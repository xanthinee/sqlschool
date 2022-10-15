package sqltask.courses;

import sqltask.connection.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursesTableDB {

    ConnectionProvider conProvider;
    MethodsForCourses coursesMethods = new MethodsForCourses();

    public CoursesTableDB(ConnectionProvider conProvider) {
        this.conProvider = conProvider;
    }


    public void putCoursesInTable(String nameOfCoursesFile) throws SQLException{

        MethodsForCourses coursesMethods = new MethodsForCourses();
        List<Course> courses = coursesMethods.makeCoursesList(nameOfCoursesFile);
        try (Connection con = conProvider.getConnection();
             PreparedStatement st = con.prepareStatement("insert into public.courses values (default,?,?)")) {
            for (Course course : courses) {
                st.setString(1, course.getName());
                st.setString(2, course.getDescription());
                st.addBatch();
            }
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteCoursesFromTable() {

        try (Connection con = conProvider.getConnection();
             PreparedStatement st = con.prepareStatement("delete from courses")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCoursesFromTable() {

        List<Course> courses = new ArrayList<>();
        try {
            Connection con = conProvider.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("select course_id, course_name, course_description " +
                    "FROM public.courses");
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

    public List<Course> getCoursesOfStudent(int studentID) {

        List<Course> coursesOfStudent = new ArrayList<>();
        try {
            Connection con = conProvider.getConnection();
            PreparedStatement getCoursesOfStud = con.prepareStatement("select c.course_id, c.course_name, c.course_description" +
                    " from courses c inner join students_courses sc " +
                    "on c.course_id = sc.course_id where student_id = ?");
            getCoursesOfStud.setInt(1, studentID);
            ResultSet coursesOfStud = getCoursesOfStud.executeQuery();
            while (coursesOfStud.next()) {
                coursesOfStudent.add(new Course(coursesOfStud.getInt("course_id"),coursesOfStud.getString("course_name"),
                        coursesOfStud.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesOfStudent;
    }

    public List<Course> findAvailableCourses(int studentID) {

        List<Course> available = new ArrayList<>();
        try (Connection con = conProvider.getConnection();
             PreparedStatement avbCourses = con.prepareStatement(" select c.course_id, c.course_name, c.course_description" +
                " from courses c " +
                "where c.course_id not in (select sc.course_id from students_courses sc where sc.student_id = ?) ")){
            avbCourses.setInt(1, studentID);
            ResultSet avbCoursesRs = avbCourses.executeQuery();
            while (avbCoursesRs.next()) {
                available.add(new Course(avbCoursesRs.getInt("course_id"), avbCoursesRs.getString("course_name").trim(),
                        avbCoursesRs.getString("course_description").trim()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return available;
    }

    public void setNewCourse(int studentID, String courseName) {
        try (Connection con = conProvider.getConnection();
                PreparedStatement addCourseToStudent = con.prepareStatement("insert into students_courses (student_id, course_id) " +
                "select ?, course_id from courses where course_name = ?")) {
            addCourseToStudent.setInt(1, studentID);
            addCourseToStudent.setString(2, courseName);
            addCourseToStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
