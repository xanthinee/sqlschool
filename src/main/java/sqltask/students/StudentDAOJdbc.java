package sqltask.students;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

public class StudentDAOJdbc {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource ds;
    private static final String STUDENT_TABLE = "students";
    private static final String GET_BY_ID = "select * from " + STUDENT_TABLE + " where student_id=?";
    private final StudentMapper studentMapper = new StudentMapper();
    private final StudentRowMapper studentsRowMapper = new StudentRowMapper();



    public StudentDAOJdbc(JdbcTemplate jdbcTemplate, DataSource ds) {
        this.jdbcTemplate = jdbcTemplate;
        this.ds = ds;
    }

    public void save(Student student) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(ds).withTableName(STUDENT_TABLE)
                .usingGeneratedKeyColumns("student_id");
        jdbcInsert.execute(studentMapper.mapToInsert(student));
    }

    public Student getById(int id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, studentsRowMapper, id);
    }
}
