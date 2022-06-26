package sqltask;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

public class CoursesTable {

    ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();

    public List<Course> makeCoursesList(String fileName) {

        FileConverter fileCon = new FileConverter();
        Stream<String> courses = fileCon.readFile(fileName);
        CoursesParser cp = new CoursesParser();
        return courses.map(cp::parse).toList();
    }

    public void putCoursesInTable(List<Course> courses) {

        try (Connection connection = conInfo.getConnection("textdata/connectioninfo.txt")) {
            for (Course course : courses) {
                String query = "insert into public.courses values (?,?,?)";
                PreparedStatement st = connection.prepareStatement(query);
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

        try (Connection connection = conInfo.getConnection("textdata/connectioninfo.txt")) {
            Statement st = connection.createStatement();
            st.executeUpdate("delete from courses");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getCoursesFromTable() {

        try (Connection connection = conInfo.getConnection("textdata/connectioninfo.txt")) {
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
