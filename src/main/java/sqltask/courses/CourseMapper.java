package sqltask.courses;

import org.flywaydb.core.internal.jdbc.RowMapper;
import sqltask.applicationmenu.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CourseMapper implements Mapper<Course> {

    private static final String ID_COLUMN = "course_id";
    private static final String NAME_COLUMN = "course_name";
    private static final String DESCRIPTION_COLUMN = "course_description";
    @Override
    public Course mapToEntity(ResultSet rs) {

        try {
            return new Course(rs.getInt(ID_COLUMN), rs.getString(NAME_COLUMN),
                    rs.getString(DESCRIPTION_COLUMN));
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

    @Override
    public Map<String, Object> mapToInsert(Course course) {

        Map<String, Object> map = new HashMap<>();
        map.put(ID_COLUMN, course.getId());
        map.put(NAME_COLUMN, course.getName());
        map.put(DESCRIPTION_COLUMN, course.getDescription());

        return map;
    }

    @Override
    public Course jdbcMapToEntity(Map<String, Object> map) {
        return new Course((Integer) map.get(ID_COLUMN),(String) map.get(NAME_COLUMN), (String) map.get(DESCRIPTION_COLUMN));
    }
}
