package sqltask.courses;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import sqltask.connection.SpringJdbcConfig;
import sqltask.students.Student;

import java.sql.SQLException;
import java.util.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

public class CourseDAOJdbc {

    SpringJdbcConfig config = new SpringJdbcConfig();
    JdbcTemplate jdbcTemplate = config.jdbcTemplate();
    private static final String COURSE_TABLE = "courses";
    CourseMapper courseMapper = new CourseMapper();

    public void saveCourse(Course course) {

        SimpleJdbcInsert jdbcInsert =
                new SimpleJdbcInsert((DataSource) config.getDataSource()).withTableName(COURSE_TABLE);
        jdbcInsert.execute(courseMapper.mapToInsert(course));
    }
}
