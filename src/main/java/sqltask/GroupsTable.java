package sqltask;

import java.sql.*;
import java.util.Random;

public class GroupsTable {

    static Random rd;
    static final int totalAmountOfGroups = 10;

    private String generateGroupName() {

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 2; i++) {
            sb.append((char) rd.nextInt('A','Z'));
        }
        sb.append("--");
        for(int i = 0; i < 2; i++) {
            sb.append(rd.nextInt(0, 9));
        }
        return sb.toString();
    }

    public void putGroupIntoTable() throws SQLException {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ConnectionInfo.host,
                    ConnectionInfo.user, ConnectionInfo.password);
            Statement st = connection.createStatement();
            for (int i = 1; i < totalAmountOfGroups + 1; i++) {
                Group newGroup = new Group(i, generateGroupName());
                st.executeUpdate("INSERT INTO public.groups VALUES('"+newGroup.getId()+"', '" +newGroup.getName()+ "')");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void deleteGroupsFromTable() throws SQLException {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ConnectionInfo.host,
                    ConnectionInfo.user, ConnectionInfo.password);
            Statement st = connection.createStatement();
            for (int i = 1; i < totalAmountOfGroups + 1; i++) {
                st.executeUpdate("DELETE FROM public.groups WHERE group_id = '" + i + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private ResultSet getGroupsFromTable() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ConnectionInfo.host, ConnectionInfo.user, ConnectionInfo.password);
            PreparedStatement getSize = connection.prepareStatement("select * FROM public.groups");
            return getSize.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        throw new IllegalStateException("ResultSet wasn't created");
    }

    public String printGroupsTable() throws SQLException{

        ResultSet rs = getGroupsFromTable();
        StringBuilder sb = new StringBuilder();
        sb.append("GROUPS:");
        sb.append(System.lineSeparator());
            while (rs.next()){
                sb.append(rs.getInt("group_id") + ". " + rs.getString("group_name"));
                sb.append(System.lineSeparator());
            }
        return sb.toString();
    }
}
