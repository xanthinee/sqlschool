package sqltask.students;

import sqltask.applicationmenu.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StudentMapper implements Mapper<Student> {

    private static final String ID_COLUMN = "student_id";
    private static final String GROUP_ID_COLUMN = "group_id";
    private static final String NAME_COLUMN = "first_name";
    private static final String SURNAME_COLUMN = "second_name";

    @Override
    public Student mapToEntity(ResultSet rs) {
        try {
            return new Student(rs.getInt(ID_COLUMN), rs.getInt(GROUP_ID_COLUMN),
                    rs.getString(NAME_COLUMN), rs.getString(SURNAME_COLUMN));
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

    @Override
    public Map<String, Object> mapToInsert(Student student) {

        Map<String, Object> map = new HashMap<>();
        map.put(ID_COLUMN, student.getStudentId());
        map.put(GROUP_ID_COLUMN, student.getGroupId());
        map.put(NAME_COLUMN, student.getName());
        map.put(SURNAME_COLUMN, student.getSurname());

        return map;
    }
}
