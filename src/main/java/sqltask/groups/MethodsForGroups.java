package sqltask.groups;

import java.util.Random;
import java.util.StringJoiner;
import java.util.*;

public class MethodsForGroups {

    Random rd = new Random();

    public String generateGroupName() {

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 2; i++) {
            sb.append((char)  rd.nextInt('A', 'Z'));
        }
        sb.append("--");
        for(int i = 0; i < 2; i++) {
            sb.append(rd.nextInt(0,10));
        }
        return sb.toString();
    }

    public String printGroupsTable(List<Group> groups)  {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("GROUPS:");
        for (Group group : groups) {
            sj.add(String.format("%-5d",group.getId()) + " - " + group.getName());
        }
        return sj.toString();
    }

    public String printResultOfComparison(List<Group> groups) {
        StringBuilder sb = new StringBuilder(System.lineSeparator());
        sb.append("GROUPS with FEWER or EQUAL amount of STUDENTS: ");
        StringJoiner sjRequired = new StringJoiner(", ");
        for (Group groupName : groups) {
            sjRequired.add(groupName.getName());
        }
        sb.append(sjRequired);
        return sb.toString();
    }
}
