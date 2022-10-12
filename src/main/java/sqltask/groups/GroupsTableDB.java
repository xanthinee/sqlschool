package sqltask.groups;

import sqltask.connection.ConnectionProvider;

import java.sql.*;
import java.util.*;

@SuppressWarnings("java:S106")
public class GroupsTableDB {

    ConnectionProvider conProvider;
    private static final int TOTAL_AMOUNT_OF_GROUPS = 10;
    MethodsForGroups groupMtd = new MethodsForGroups();

    public GroupsTableDB(ConnectionProvider conProvider) {
        this.conProvider = conProvider;
    }

    public void putGroupIntoTable() {
        try (Connection con = conProvider.getConnection();
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

    public List<Integer> groupsIdList() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        try {
            Connection con = conProvider.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("select group_id FROM public.groups");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public void deleteGroupsFromTable() {

        try (Connection con = conProvider.getConnection();
             PreparedStatement st = con.prepareStatement("DELETE FROM public.groups");) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Group> getGroupsFromTable() {
        List<Group> groups = new ArrayList<>();
        try {
            Connection con = conProvider.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("select group_id, group_name FROM public.groups");
            ResultSet groupsRS = preparedStatement.executeQuery();
            while (groupsRS.next()) {
                groups.add(new Group(groupsRS.getInt("group_id"), groupsRS.getString("group_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public List<Group> compareGroups(int groupID) {

        List<Group> groupMembers = new ArrayList<>();
        try (Connection con = conProvider.getConnection();
             PreparedStatement getGroupsMembers = con.prepareStatement("select g.group_id, g.group_name " +
                     "from groups g left join students s on g.group_id = s.group_id " +
                     "where g.group_id <> ? " +
                     "group by g.group_id, g.group_name " +
                     "having count(s.student_id) <= (select count(s2.student_id) from students s2 where s2.group_id = ?)")) {
            getGroupsMembers.setInt(1, groupID);
            getGroupsMembers.setInt(2, groupID);
            ResultSet rs = getGroupsMembers.executeQuery();
            while (rs.next()) {
                groupMembers.add(new Group(rs.getInt("group_id"), rs.getString("group_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupMembers;
    }
}
