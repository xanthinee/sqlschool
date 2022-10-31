package sqltask.groups;

import java.util.ArrayList;
import java.util.List;

public class GroupService {

    private final GroupDAO dao;
    private static final int TOTAL_AMOUNT_OF_GROUPS = 10;

    public GroupService(GroupDAO dao) {
        this.dao = dao;
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    public List<Group> getAll() {
        return dao.getAll();
    }

    public Group getById(int id) {
        return dao.getById(id);
    }

    public void deleteById(int id) {
        dao.deleteById(id);
    }

    public List<Group> compareGroups(int groupID) {
        return dao.compareGroups(groupID);
    }

    public List<Group> generateGroups() {

        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < TOTAL_AMOUNT_OF_GROUPS; i++) {
            groups.add(new Group(null, GroupUtils.generateGroupName()));
        }
        return groups;
    }
}
