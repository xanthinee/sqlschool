package sqltask.groups;

import sqltask.applicationmenu.DAO;

import java.util.*;

public interface GroupDAO extends DAO<Group> {

    List<Group> compareGroups(int groupId);
    void putGroupIntoTable();
}
