package sqltask.groups;

import java.sql.*;
import java.util.*;

@SuppressWarnings("java:S106")
public class GroupsTableDB {

    private static final int TOTAL_AMOUNT_OF_GROUPS = 10;
    Random rd = new Random();
    MethodsForGroups groupMtd = new MethodsForGroups();
    Scanner sc = new Scanner(System.in);

    public void putGroupIntoTable(Connection con) {
        try (PreparedStatement st = con.prepareStatement("INSERT INTO public.groups VALUES(?,?)")){
            con.setAutoCommit(false);
            for (int i = 0; i < TOTAL_AMOUNT_OF_GROUPS; i++) {
                st.setInt(1, rd.nextInt(1000, 10000));
                st.setString(2, groupMtd.generateGroupName());
                st.addBatch();
            }
            st.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> groupsIdList(Connection con) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("select * FROM public.groups");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public void deleteGroupsFromTable(Connection con) {

        try (Connection connection = con;
             PreparedStatement st = connection.prepareStatement("DELETE FROM public.groups");) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Group> getGroupsFromTable(Connection con) {
        List<Group> groups = new ArrayList<>();
        try {
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

    public String compareGroups(Connection con) {

        System.out.println("Enter GROUP to COMPARE below: ");
        int groupID = sc.nextInt();
        List<String> groupMembers = new ArrayList<>();
        try (Connection connection = con;
             PreparedStatement getGroupsMembers = connection.prepareStatement("select g.group_name " +
                     "from groups g left join students s on g.group_id = s.group_id " +
                     "where g.group_id <> ? " +
                     "group by g.group_name " +
                     "having count(s.student_id) <= (select count(s2.student_id) from students s2 where s2.group_id = ?) " +
                     "order by g.group_name")) {
            getGroupsMembers.setInt(1, groupID);
            getGroupsMembers.setInt(2, groupID);
            ResultSet rs = getGroupsMembers.executeQuery();
            while (rs.next()) {
                groupMembers.add(rs.getString("group_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder(System.lineSeparator());
        sb.append("GROUPS with FEWER or EQUAL amount of STUDENTS: ");
        StringJoiner sjRequired = new StringJoiner(", ");
        for (String groupName : groupMembers) {
            sjRequired.add(groupName);
        }
        sb.append(sjRequired);
        return sb.toString();
    }
}
