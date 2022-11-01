package sqltask.courses;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import sqltask.connection.SpringJdbcConfig;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import sqltask.students.Student;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import org.springframework.jdbc.core.simple.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import javax.sql.DataSource;

@Repository
public class CourseDAOJdbc {

    SpringJdbcConfig config = new SpringJdbcConfig();
    private static final String COURSE_TABLE = "courses";
    private static final String GET_BY_ID = "select * from " + COURSE_TABLE + " where course_id = :course_id";
    private final JdbcTemplate jdbcTemplate;
    CourseMapper courseMapper = new CourseMapper();

    public CourseDAOJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveCourse(Course course) {

        SimpleJdbcInsert jdbcInsert =
                new SimpleJdbcInsert(config.getDataSource()).withTableName(COURSE_TABLE)
                        .usingGeneratedKeyColumns("course_id");
        jdbcInsert.execute(courseMapper.mapToInsert(course));
    }
    public Course getById(int id) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(config.getDataSource())
                .withProcedureName(GET_BY_ID);
        SqlParameterSource in = new MapSqlParameterSource().addValue("course_id", id);
        Map<String, Object> map = simpleJdbcCall.execute(in);

        return courseMapper.jdbcMapToEntity(map);
    }
}
