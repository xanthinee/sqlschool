package sqltask.groups;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.StringJoiner;
import java.util.*;

public class GroupsMethods {

    static final int TOTAL_AMOUNT_OF_GROUPS = 10;

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

    public String printGroupsTable(List<Group> groups) throws SQLException {

        StringJoiner sj = new StringJoiner("");
        sj.add("GROUPS:");
        sj.add(System.lineSeparator());
        for (Group group : groups) {
            sj.add(group.getId() + " - " + group.getName());
            sj.add(System.lineSeparator());
        }
        return sj.toString();
    }
}
