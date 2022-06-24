package sqltask;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

public class CoursesTable {

    ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    UserConnection userCon = conInfo.getConnectionInfo("textdata/connectioninfo.txt");

    public List<Course> makeCoursesList(String fileName) {

        FileConverter fileCon = new FileConverter();
        Stream<String> courses = fileCon.readFile(fileName);
        CoursesParser cp = new CoursesParser();
        return courses.map(cp::parse).toList();
    }

    public void putCoursesInTable(List<Course> courses) throws SQLException {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(userCon.host, userCon.user, userCon.password);
            for (Course course : courses) {
                String query = "insert into public.courses values (?,?,?)";
                PreparedStatement st = connection.prepareStatement(query);
                st.setInt(1, course.getId());
                st.setString(2, course.getName());
                st.setString(3, course.getDescription());
                st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    public void deleteCoursesFromTable() throws SQLException{

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(userCon.host, userCon.user, userCon.password);
            Statement st = connection.createStatement();
            st.executeUpdate("delete from courses");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private ResultSet getCoursesFromTable() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(userCon.host, userCon.user, userCon.password);
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM public.courses");
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
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
