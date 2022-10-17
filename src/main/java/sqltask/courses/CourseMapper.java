package sqltask.courses;

import sqltask.applicationmenu.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements Mapper<Course> {

    @Override
    public Course mapToEntity(ResultSet rs) {

        Course course = new Course(null, null, null);
        try {
            course.setId(rs.getInt("course_id"));
            course.setName("course_name");
            course.setDescription("course_description");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    @Override
    public void mapToRow(PreparedStatement ps, Course entity) {

        try {
            ps.setInt(1, entity.getId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
