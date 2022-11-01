package sqltask.groups;

import sqltask.applicationmenu.Mapper;
import sqltask.courses.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GroupMapper implements Mapper<Group> {

    private static final String ID_COLUMN = "group_id";
    private static final String NAME_COLUMN = "group_name";

    @Override
    public Group mapToEntity(ResultSet rs) {

        try {
            return new Group(rs.getInt(ID_COLUMN), rs.getString(NAME_COLUMN));
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

    @Override
    public Map<String, Object> mapToInsert(Group group) {

        Map<String, Object> map = new HashMap<>();
        map.put(ID_COLUMN, group.getId());
        map.put(NAME_COLUMN, group.getName());

        return map;
    }

    @Override
    public Group jdbcMapToEntity(Map<String, Object> map) {
        return new Group((Integer) map.get(ID_COLUMN), (String) map.get(NAME_COLUMN));
    }
}
