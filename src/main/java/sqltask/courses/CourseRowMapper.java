package sqltask.courses;

//import org.flywaydb.core.internal.jdbc.RowMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet resultSet, int rowNum) {
        try {
            return new Course(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
