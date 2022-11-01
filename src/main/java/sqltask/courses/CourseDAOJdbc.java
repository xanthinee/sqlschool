package sqltask.courses;

//import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import sqltask.connection.SpringJdbcConfig;

import java.sql.SQLException;
import java.util.*;
import org.springframework.jdbc.core.simple.*;

import javax.sql.DataSource;

@SuppressWarnings("java:S1874")
@Repository
public class CourseDAOJdbc {

    private final DataSource ds;
    private static final String COURSE_TABLE = "courses";
    private static final String GET_BY_ID = "select * from " + COURSE_TABLE + " where course_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper = new CourseMapper();
    private final CourseRowMapper courseRowMapper = new CourseRowMapper();

    public CourseDAOJdbc(JdbcTemplate jdbcTemplate, DataSource ds) {
        this.jdbcTemplate = jdbcTemplate;
        this.ds = ds;
    }

    public void saveCourse(Course course) {

        SimpleJdbcInsert jdbcInsert =
                new SimpleJdbcInsert(ds).withTableName(COURSE_TABLE)
                        .usingGeneratedKeyColumns("course_id");
        jdbcInsert.execute(courseMapper.mapToInsert(course));
    }
    public Course getById(int id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, courseRowMapper, id);
    }
}
