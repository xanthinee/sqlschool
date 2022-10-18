package sqltask.groups;

import java.util.List;

public class GroupService {

    private final GroupDAOImpl dao;

    public GroupService(GroupDAOImpl dao) {
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
}
