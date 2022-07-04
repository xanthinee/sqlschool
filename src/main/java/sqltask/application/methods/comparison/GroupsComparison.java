package sqltask.application.methods.comparison;

import sqltask.application.menu.Menu;
import sqltask.groups.Group;
import sqltask.groups.GroupsTableDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GroupsComparison implements Menu {

    Scanner sc = new Scanner(System.in);

    @Override
    public String getMenuText() {
        return "Compare GROUP with others";
    }

    @Override
    public String doAction(Connection con) {

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
