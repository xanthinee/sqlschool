package sqltask;

import java.sql.*;
import java.util.Random;
import java.util.*;

public class GroupsTable {

    static Random rd;
    static final int totalAmountOfGroups = 10;

    ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    UserConnection userCon = conInfo.getConnectionInfo("textdata/connectioninfo.txt");

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
            connection = DriverManager.getConnection(userCon.host, userCon.user, userCon.password);
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
            connection = DriverManager.getConnection(userCon.host, userCon.user, userCon.password);
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
            connection = DriverManager.getConnection(userCon.host, userCon.user,userCon.password);
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM public.groups");
            return preparedStatement.executeQuery();
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
        StringJoiner sj = new StringJoiner("");
        sj.add("GROUPS:");
        sj.add(System.lineSeparator());
            while (rs.next()){
                sj.add(rs.getInt("group_id") + ". " + rs.getString("group_name").trim());
                if (!rs.isLast()) {
                    sj.add(System.lineSeparator());
                }
            }
        return sj.toString();
    }
}
