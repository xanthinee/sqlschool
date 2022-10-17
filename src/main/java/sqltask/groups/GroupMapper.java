package sqltask.groups;

import sqltask.applicationmenu.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements Mapper<Group> {

    @Override
    public Group mapToEntity(ResultSet rs) {

        Group group = new Group(null, null);
        try {
            group.setId(rs.getInt("group_id"));
            group.setName(rs.getString("group_name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }

    @Override
    public void mapToRow(PreparedStatement ps, Group entity){

        try {
            ps.setInt(1, entity.getId());
            ps.setString(2, entity.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
