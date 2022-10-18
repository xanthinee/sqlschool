package sqltask.groups;

import sqltask.connection.DataSource;

import java.sql.*;
import java.util.*;

@SuppressWarnings("java:S106")
public class GroupsTableDB implements GroupDAO{

    DataSource ds;
    private static final int TOTAL_AMOUNT_OF_GROUPS = 10;
    MethodsForGroups groupMtd = new MethodsForGroups();
    private final String tableName;
    GroupMapper groupMapper = new GroupMapper();

    private static final String GROUP_ID = "group_id";
    private static final String GROUP_NAME = "group_name";

    public GroupsTableDB(DataSource ds, String tableName) {
        this.ds = ds;
        this.tableName = tableName;
    }

    @Override
    public void putGroupIntoTable() {
        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement("INSERT INTO public.groups VALUES(default,?)")){
            for (int i = 0; i < TOTAL_AMOUNT_OF_GROUPS; i++) {
                st.setString(1, groupMtd.generateGroupName());
                st.addBatch();
            }
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {

        try (Connection con = ds.getConnection();
             PreparedStatement st = con.prepareStatement("DELETE FROM public.groups");) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {

        try (Connection con = ds.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("delete from groups where group_id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("select group_id, group_name FROM public.groups");) {
            ResultSet groupsRS = preparedStatement.executeQuery();
            while (groupsRS.next()) {
                groups.add(groupMapper.mapToEntity(groupsRS));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public List<Group> compareGroups(int groupID) {

        List<Group> groupMembers = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement getGroupsMembers = con.prepareStatement("select g.group_id, g.group_name " +
                     "from groups g left join students s on g.group_id = s.group_id " +
                     "where g.group_id <> ? " +
                     "group by g.group_id, g.group_name " +
                     "having count(s.student_id) <= (select count(s2.student_id) from students s2 where s2.group_id = ?)")) {
            getGroupsMembers.setInt(1, groupID);
            getGroupsMembers.setInt(2, groupID);
            ResultSet rs = getGroupsMembers.executeQuery();
            while (rs.next()) {
                groupMembers.add(new Group(rs.getInt(GROUP_ID), rs.getString(GROUP_NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupMembers;
    }

    @Override
    public Group getById(int id) {

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from " + tableName + " where group_id = ? ")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return groupMapper.mapToEntity(rs);
            }
            throw new IllegalStateException("No data found for id " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
