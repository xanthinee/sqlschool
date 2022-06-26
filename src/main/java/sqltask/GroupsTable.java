package sqltask;

import java.sql.*;
import java.util.Random;
import java.util.*;

public class GroupsTable {

    static Random rd = new Random();
    static final int totalAmountOfGroups = 10;

    ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();

    private String generateGroupName(Random rd) {

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 2; i++) {
            sb.append((char) rd.nextInt('A','Z'));
        }
        sb.append("--");
        for(int i = 0; i < 2; i++) {
            sb.append(rd.nextInt(0, 10));
        }
        return sb.toString();
    }

    public void putGroupIntoTable() {

        try (Connection connection = conInfo.getConnection("textdata/connectioninfo.txt")) {
            for (int i = 0; i < totalAmountOfGroups; i++) {
                String query = "INSERT INTO public.groups VALUES(?,?)";
                PreparedStatement st = connection.prepareStatement(query);
                st.setInt(1,rd.nextInt(1000,10000));
                st.setString(2, generateGroupName(rd));
                st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGroupsFromTable() {

        try (Connection connection = conInfo.getConnection("textdata/connectioninfo.txt")) {
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM public.groups");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getGroupsFromTable() {
        try (Connection connection = conInfo.getConnection("textdata/connectioninfo.txt")) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM public.groups");
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("ResultSet wasn't created");
    }

    public String printGroupsTable() throws SQLException{

        ResultSet rs = getGroupsFromTable();
        StringJoiner sj = new StringJoiner("");
        sj.add("GROUPS:");
        sj.add(System.lineSeparator());
            while (rs.next()){
                sj.add(rs.getInt("group_id") + " - " + rs.getString("group_name").trim());
                if (!rs.isLast()) {
                    sj.add(System.lineSeparator());
                }
            }
        return sj.toString();
    }
}
