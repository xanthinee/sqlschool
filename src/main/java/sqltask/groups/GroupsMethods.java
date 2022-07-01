package sqltask.groups;

import java.util.Random;
import java.util.StringJoiner;
import java.util.*;

public class GroupsMethods {

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
            sj.add(group.getId() + " - " + group.getName());
        }
        return sj.toString();
    }
}
