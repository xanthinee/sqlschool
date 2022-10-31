package sqltask.students;

import sqltask.applicationmenu.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements Mapper<Student> {

    @Override
    public Student mapToEntity(ResultSet rs) {
        try {
            return new Student(rs.getInt("student_id"), rs.getInt("group_id"),
                    rs.getString("first_name"), rs.getString("second_name"));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void mapToRow(PreparedStatement ps, Student entity) {
        try {
            ps.setString(1,entity.getName());
            ps.setString(2, entity.getSurname());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
