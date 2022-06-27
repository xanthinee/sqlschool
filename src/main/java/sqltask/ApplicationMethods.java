package sqltask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ApplicationMethods {

    ConnectionInfoGenerator conInfo = new ConnectionInfoGenerator();
    String connectionFile = "data/connectioninfo";

    public String compareGroup(int groupID) throws SQLException {

        GroupsTable gt = new GroupsTable();
        ResultSet groupsRS = gt.getGroupsFromTable();
        Map<String, Integer> groupMembers = new HashMap<>();
        try (Connection connection = conInfo.getConnection(connectionFile)) {
            PreparedStatement prepSt = connection.prepareStatement("select g.group_name, count(s.student_id) from groups g " +
                    "left outer join students s on g.group_id = s.group_id group by g.group_name");
            ResultSet rs = prepSt.executeQuery();
            while (rs.next()) {
                groupMembers.put(rs.getString("group_name"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Map<Integer, Group> groups = new HashMap<>();
        while (groupsRS.next()) {
            groups.put(groupsRS.getInt("group_id"), new Group(groupsRS.getInt("group_id"),
                    groupsRS.getString("group_name")));
        }

        int amountOfStudentsInInputGroup = groupMembers.get(groups.get(groupID).getName());
        List<String> withEqualSize = new ArrayList<>();
        List<String> withFewerSize = new ArrayList<>();
        for (Map.Entry<Integer, Group> entry : groups.entrySet()) {
            if (entry.getKey() != groupID && (groupMembers.get(entry.getValue().getName()) < amountOfStudentsInInputGroup)) {
                withFewerSize.add(entry.getValue().getName());
            }
            if (entry.getKey() != groupID && groupMembers.get(entry.getValue().getName()) == amountOfStudentsInInputGroup) {
                withEqualSize.add(entry.getValue().getName());
            }
        }

        StringJoiner sj = new StringJoiner("");
        if (withFewerSize.size() == 0) {
            sj.add("There no group with FEWER amount of students.");
        } else {
            sj.add("Groups with fewer amount of students: ");
            for (String names : withFewerSize) {
                sj.add(names + "; ");
            }
        }
        sj.add(System.lineSeparator());

        if (withEqualSize.size() == 0) {
            sj.add("There no group with EQUAL amount of students.");
        } else {
            sj.add("Groups with equal amount of students: ");
            for (String names : withEqualSize) {
                sj.add(names + "; ");
            }
        }
        return sj.toString();
        }
    }