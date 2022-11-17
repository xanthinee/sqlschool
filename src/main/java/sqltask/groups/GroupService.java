package sqltask.groups;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GroupService {

    @Autowired
    private final GroupDaoJdbc groupDaoJdbc;
    private static final int TOTAL_AMOUNT_OF_GROUPS = 10;

    public GroupService(GroupDaoJdbc groupDaoJdbc) {
        this.groupDaoJdbc = groupDaoJdbc;
    }

    public void deleteAll() {
        groupDaoJdbc.deleteAll();
    }

    public List<Group> getAll() {
        return groupDaoJdbc.getAll();
    }

    public Group getById(int id) {
        return groupDaoJdbc.getById(id);
    }

    public void deleteById(int id) {
        groupDaoJdbc.deleteById(id);
    }

    public List<Group> compareGroups(int groupID) {
        return groupDaoJdbc.compareGroups(groupID);
    }

    public List<Group> generateGroups() {

        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < TOTAL_AMOUNT_OF_GROUPS; i++) {
            groups.add(new Group(null, GroupUtils.generateGroupName()));
        }
        return groups;
    }
}
