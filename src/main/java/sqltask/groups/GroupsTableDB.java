package sqltask.groups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import sqltask.connection.*;

public class GroupsTableDB {

    private static final int TOTAL_AMOUNT_OF_GROUPS = 10;
    Random rd = new Random();
    GroupsMethods groupMtd = new GroupsMethods();

    public void putGroupIntoTable(Connection con) {
        try (PreparedStatement st = con.prepareStatement("INSERT INTO public.groups VALUES(?,?)")) {
            for (int i = 0; i < TOTAL_AMOUNT_OF_GROUPS; i++) {
                st.setInt(1, rd.nextInt(1000, 10000));
                st.setString(2, groupMtd.generateGroupName());
                st.executeUpdate();
            }
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
            PreparedStatement preparedStatement = con.prepareStatement("select * FROM public.groups");
            ResultSet groupsRS = preparedStatement.executeQuery();
            while (groupsRS.next()) {
                groups.add(new Group(groupsRS.getInt("group_id"), groupsRS.getString("group_name").trim()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }
}
