package sqltask.courses;

import sqltask.applicationmenu.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements Mapper<Course> {

    @Override
    public Course mapToEntity(ResultSet rs) {

        try {
            return new Course(rs.getInt("course_id"), rs.getString("course_name"),
                    rs.getString("course_description"));
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void mapToRow(PreparedStatement ps, Course entity) {

        try {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
