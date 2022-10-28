package sqltask.groups;

import sqltask.connection.DataSource;

import java.sql.*;
import java.util.*;

@SuppressWarnings("java:S106")
public class GroupDAOImpl implements GroupDAO{

    DataSource ds;
    private static final String GROUPS_TABLE = "groups";
    GroupMapper groupMapper = new GroupMapper();

    public GroupDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public void save(Group group) {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO " + GROUPS_TABLE + " VALUES(default,?)")){
                groupMapper.mapToRow(ps,group);
                ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAll(List<Group> groups) {
        try (Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement(" insert into " + GROUPS_TABLE + " values(default,?)")){
            for (Group group : groups) {
                groupMapper.mapToRow(ps, group);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM public.groups");) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select group_id, group_name FROM public.groups");) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                groups.add(groupMapper.mapToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public void deleteById(int id) {

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("delete from groups where group_id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Group getById(int id) {

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from " + GROUPS_TABLE + " where group_id = ? ")) {
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

    @Override
    public List<Group> compareGroups(int groupID) {

        List<Group> groupMembers = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement("select g.group_id, g.group_name " +
                     "from groups g left join students s on g.group_id = s.group_id " +
                     "where g.group_id <> ? " +
                     "group by g.group_id, g.group_name " +
                     "having count(s.student_id) <= (select count(s2.student_id) from students s2 where s2.group_id = ?)")) {
            ps.setInt(1, groupID);
            ps.setInt(2, groupID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                groupMembers.add(groupMapper.mapToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupMembers;
    }
}
