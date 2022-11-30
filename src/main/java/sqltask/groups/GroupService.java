package sqltask.groups;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    private final GroupDAO groupDao;
    private static final int TOTAL_AMOUNT_OF_GROUPS = 10;

    public GroupService(GroupDAO groupDao) {
        this.groupDao = groupDao;
    }

    public void deleteAll() {
        groupDao.deleteAll();
    }

    public List<Group> getAll() {
        return groupDao.getAll();
    }

    public Group getById(int id) {
        return groupDao.getById(id);
    }

    public void deleteById(int id) {
        groupDao.deleteById(id);
    }

    public List<Group> compareGroups(int groupID) {
        return groupDao.compareGroups(groupID);
    }

    public void saveAll(List<Group> groups) {
        groupDao.saveAll(groups);
    }

    public List<Group> generateGroups() {

        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < TOTAL_AMOUNT_OF_GROUPS; i++) {
            groups.add(new Group(null, GroupUtils.generateGroupName()));
        }
        return groups;
    }
}
