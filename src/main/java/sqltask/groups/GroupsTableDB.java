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

    static final int TOTAL_AMOUNT_OF_GROUPS = 10;

    private final ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    Random rd = new Random();
    String connectionFile = "data/connectioninfo";
    GroupsMethods groupMet = new GroupsMethods();

    public void putGroupIntoTable() {
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            for (int i = 0; i < TOTAL_AMOUNT_OF_GROUPS; i++) {
                PreparedStatement st = connection.prepareStatement("INSERT INTO public.groups VALUES(?,?)");
                st.setInt(1, rd.nextInt(1000, 10000));
                st.setString(2, groupMet.generateGroupName());
                st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> groupsIdList() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM public.groups");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        }
        return ids;
    }

    public void deleteGroupsFromTable() {

        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement st = connection.prepareStatement("DELETE FROM public.groups");
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Group> getGroupsFromTable() {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM public.groups");
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
