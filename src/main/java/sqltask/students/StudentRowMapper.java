package sqltask.students;


import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int rowNum) {

        try {
            return new Student(resultSet.getInt(1), resultSet.getInt(2),
                    resultSet.getString(3), resultSet.getString(4));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
