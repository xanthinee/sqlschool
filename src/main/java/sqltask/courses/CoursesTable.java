package sqltask.courses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sqltask.connection.*;

public class CoursesTable {

    private final ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    private static final String CONNECTION_FILE = "data/connectioninfo";

    public void putCoursesInTable(List<Course> courses) {

        try (Connection connection = conInfo.getConnection(CONNECTION_FILE);
             PreparedStatement st = connection.prepareStatement("insert into public.courses values (?,?,?)")) {
            for (Course course : courses) {
                st.setInt(1, course.getId());
                st.setString(2, course.getName());
                st.setString(3, course.getDescription());
                st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteCoursesFromTable() {

        try (Connection connection = conInfo.getConnection(CONNECTION_FILE);
             PreparedStatement st = connection.prepareStatement("delete from courses")) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Course> getCoursesFromTable() {

        List<Course> courses = new ArrayList<>();
        try (Connection connection = conInfo.getConnection(CONNECTION_FILE);
             PreparedStatement preparedStatement = connection.prepareStatement("select * FROM public.courses")) {
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
