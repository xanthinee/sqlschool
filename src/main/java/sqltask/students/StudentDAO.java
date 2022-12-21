package sqltask.students;

import sqltask.applicationmenu.DAO;

public interface StudentDAO extends DAO<Student>{

    void updateGroupIdByStudId(Student student, int groupID);
}
