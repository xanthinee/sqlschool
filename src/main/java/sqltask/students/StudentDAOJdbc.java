package sqltask.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
public class StudentDAOJdbc implements StudentDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final String STUDENT_TABLE = "students";
    private static final String GET_BY_ID = "select * from " + STUDENT_TABLE + " where student_id=?";
    private static final String SAVE_STUDENT = "insert into " + STUDENT_TABLE + " values(default,null,?,?)";
    private static final String DELETE_BY_ID = "delete from " + STUDENT_TABLE + " where student_id = ?";
    private static final String DELETE_ALL = "delete from " + STUDENT_TABLE;
    private static final String GET_ALL = "select * from " + STUDENT_TABLE;
    private static final String UPGRADE_GROUP_ID_OF_STUDENT = "update " + STUDENT_TABLE + " set group_id = ? where student_id = ?";
    private final StudentRowMapper studentsRowMapper = new StudentRowMapper();

    @Autowired
    public StudentDAOJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Student student) {
        jdbcTemplate.update(SAVE_STUDENT, student.getName(), student.getSurname());
    }

    @Override
    public void saveAll(List<Student> students) {
        jdbcTemplate.batchUpdate(SAVE_STUDENT, students, students.size(),
                (PreparedStatement ps, Student student) -> {
            ps.setString(1, student.getName());
            ps.setString(2, student.getSurname());
                });
    }

    @Override
    public Student getById(int id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, studentsRowMapper, id);
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query(GET_ALL,studentsRowMapper);
    }

    @Override
    public void updateGroupIdByStudId(Student student, int groupID) {
        jdbcTemplate.update(UPGRADE_GROUP_ID_OF_STUDENT, groupID, student.getStudentId());
    }
}
