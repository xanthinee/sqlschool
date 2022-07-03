package sqltask.courses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursesTableDB {

    public void putCoursesInTable(Connection con, List<Course> courses) throws SQLException{

        try (Connection connection = con;
             PreparedStatement st = connection.prepareStatement("insert into public.courses values (default,?,?)")) {
            for (Course course : courses) {
                st.setString(1, course.getName());
                st.setString(2, course.getDescription());
                st.executeUpdate();
            }
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
}
