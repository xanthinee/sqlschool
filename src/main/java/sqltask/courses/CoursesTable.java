package sqltask.courses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;
import sqltask.connection.*;
import sqltask.helpers.*;

public class CoursesTable {

    private final ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();

    public List<Course> makeCoursesList(String fileName) {

        customFileReader fileCon = new customFileReader();
        Stream<String> courses = fileCon.readFile(fileName);
        CoursesParser cp = new CoursesParser();
        return courses.map(cp::parse).toList();
    }

    public void putCoursesInTable(List<Course> courses) {

        try (Connection connection = conInfo.getConnection("data/connectioninfo")) {
            for (Course course : courses) {
                PreparedStatement st = connection.prepareStatement("insert into public.courses values (?,?,?)");
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

        try (Connection connection = conInfo.getConnection("data/connectioninfo")) {
            PreparedStatement st = connection.prepareStatement("delete from courses");
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getCoursesFromTable() {

        try (Connection connection = conInfo.getConnection("data/connectioninfo")) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM public.courses");
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("ResultSet wasn't created");
    }

    public String printCoursesTable() throws SQLException{

        ResultSet rs = getCoursesFromTable();
        StringJoiner sj = new StringJoiner("");
        sj.add("COURSES:");
        sj.add(System.lineSeparator());
        while (rs.next()){
                sj.add(rs.getInt("course_id") + ". " + rs.getString("course_name").trim()
                        + ": " + rs.getString("course_description").trim());
                if (!rs.isLast()) {
                    sj.add(System.lineSeparator());
                }
            }
        return sj.toString();
    }
}
