package sqltask.application.methods;

import sqltask.groups.Group;
import sqltask.groups.GroupsTableDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CompareGroups {

    Scanner sc = new Scanner(System.in);

    private String resultOfComparison(List<String> withEqualSize, List<String> withFewerSize) {

        StringJoiner sj = new StringJoiner("");
        if (withFewerSize.isEmpty()) {
            sj.add("There no group with FEWER amount of students.");
        } else {
            sj.add("Groups with FEWER amount of students: ");
            for (String names : withFewerSize) {
                sj.add(names + "; ");
            }
        }
        sj.add(System.lineSeparator());

        if (withEqualSize.isEmpty()) {
            sj.add("There no group with EQUAL amount of students.");
        } else {
            sj.add("Groups with EQUAL amount of students: ");
            for (String names : withEqualSize) {
                sj.add(names + "; ");
            }
        }
        return sj.toString();
    }

    public String compareGroup(Connection con) {

        GroupsTableDB groupsDB = new GroupsTableDB();
        List<Group> groupsList = groupsDB.getGroupsFromTable(con);
        System.out.println("Enter GROUP to COMPARE bellow: ");
        int groupID = sc.nextInt();
        Map<String, Integer> groupMembers = new HashMap<>();
        try (Connection connection = con;
             PreparedStatement getGroupsMembers = connection.prepareStatement("select g.group_name, count(s.student_id) from groups g " +
                     "left outer join students s on g.group_id = s.group_id group by g.group_name")) {
            ResultSet rs = getGroupsMembers.executeQuery();
            while (rs.next()) {
                groupMembers.put(rs.getString("group_name"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Map<Integer, Group> groups = new HashMap<>();
        for (Group group : groupsList) {
            groups.put(group.getId(), group);
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
        System.out.println(resultOfComparison(withEqualSize, withFewerSize));
        return resultOfComparison(withEqualSize, withFewerSize);
    }
}
