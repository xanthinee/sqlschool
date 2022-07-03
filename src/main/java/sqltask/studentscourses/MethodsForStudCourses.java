package sqltask.studentscourses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

public class MethodsForStudCourses {

    public String printStudCourseTable(ResultSet rs) throws SQLException {

        StringJoiner sj = new StringJoiner("");
        sj.add("STUDENTS's COURSES");
        sj.add(System.lineSeparator());
        while (rs.next()) {
            sj.add(rs.getInt("student_id") + " | ");
            sj.add(String.format("%-20s", rs.getString("course_name")) + "  | ");
            if (!rs.isLast()) {
                sj.add(System.lineSeparator());
            }
        }
        return sj.toString();
    }
}
