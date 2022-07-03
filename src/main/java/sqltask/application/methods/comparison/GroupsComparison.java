package sqltask.application.methods.comparison;

import sqltask.application.menu.IMenu;
import sqltask.groups.Group;
import sqltask.groups.GroupsTableDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GroupsComparison implements IMenu {

    Scanner sc = new Scanner(System.in);

    @Override
    public String getMenuText() {
        return "Compare GROUP with others";
    }

    @Override
    public String doAction(Connection con) {

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
        StringBuilder sb = new StringBuilder(System.lineSeparator());
        sb.append("GROUPS with FEWER amount of STUDENTS: ");
        StringJoiner sjFewer = new StringJoiner(", ");
        StringJoiner sjEqual = new StringJoiner(", ");
        for (Map.Entry<Integer, Group> entry : groups.entrySet()) {
            if (entry.getKey() != groupID && (groupMembers.get(entry.getValue().getName()) < amountOfStudentsInInputGroup)) {
                sjFewer.add(entry.getValue().getName());
            }
            if (entry.getKey() != groupID && groupMembers.get(entry.getValue().getName()) == amountOfStudentsInInputGroup) {
                sjEqual.add(entry.getValue().getName());
            }
        }
        sb.append(sjFewer).append(System.lineSeparator()).append("GROUPS with EQUAL amount of STUDENTS: ").append(sjEqual);
        return sb.toString();
    }
}
