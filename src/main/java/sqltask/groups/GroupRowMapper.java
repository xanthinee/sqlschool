package sqltask.groups;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet resultSet, int rowNum) {
        try {
            return new Group(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
           throw new IllegalStateException(e);
        }
    }
}
