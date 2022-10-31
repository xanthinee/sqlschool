package sqltask.groups;

import sqltask.applicationmenu.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements Mapper<Group> {

    @Override
    public Group mapToEntity(ResultSet rs) {

        try {
            return new Group(rs.getInt("group_id"), rs.getString("group_name"));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void mapToRow(PreparedStatement ps, Group entity){

        try {
            ps.setString(1, entity.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
